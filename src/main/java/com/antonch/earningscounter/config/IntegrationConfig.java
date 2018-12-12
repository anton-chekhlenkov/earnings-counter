package com.antonch.earningscounter.config;

import com.antonch.earningscounter.ApplicationContextProvider;
import com.antonch.earningscounter.MessageFactory;
import com.antonch.earningscounter.domain.CurrencyRateData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Конфигурация каналов Spring Integration
 * для получения данных о курсах валют
 * из внешнего сервиса
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
@Slf4j
@Configuration
@EnableIntegration
public class IntegrationConfig {


    /**
     * Адрес сервиса курсов валют
     */

    @Value("${integration.url}")
    String resourceUrl;


    /**
     * Ключ авторизации для сервисов курсов валют
     */

    @Value("${integration.access.key}")
    String accessKey;


    /**
     * Источник сообщений для переодического опроса текущего курса
     */

    @Bean
    @InboundChannelAdapter(value = "requestRateChannel", poller = @Poller(fixedDelay = "${integration.request.latest.rate.delay}"))
    public MessageSource<Message> requestLatestRateMessageSource() {
        return new AbstractMessageSource<Message>() {
            @Override
            protected Message doReceive() {
                return MessageFactory.REQUEST_LATEST_RATE_MESSAGE;
            }

            @Override
            public String getComponentType() {
                return "currency-rate:inbound-channel-adapter";
            }
        };
    }


    /**
     * Сервис для отправки запросов на получение данных о курсах валют
     */

    @MessagingGateway
    public interface IntegrationService {

        @Gateway(requestChannel = "requestRateChannel")
        List<String> requestRate(@Payload Message msg);

    }

    /**
     * Канал для запуска процесса получения данных о курсах валют
     */

    @Bean
    public MessageChannel requestRateChannel() {
        return new DirectChannel();
    }


    /**
     * Шлюз обращения к сервису курсов валют
     */

    @Bean
    public IntegrationFlow requestRateFlow() {
        return IntegrationFlows.from("requestRateChannel")
                .handle(
                        Http.outboundGateway(msg ->
                                UriComponentsBuilder.fromHttpUrl(resourceUrl)
                                        .path((String) msg.getHeaders().get("service"))
                                        .queryParam("access_key", accessKey)
                                        .queryParams((MultiValueMap) msg.getPayload())
                                        .build().toUriString()
                        )
                                .httpMethod(HttpMethod.GET)
                                .expectedResponseType(String.class)
                                .charset("UTF-8")
                )
                .transform(Transformers.fromJson(CurrencyRateData.class))
                .channel("receiveRateChannel")
                .get();
    }


    /**
     * Канал для получения данных о курсах валют
     */

    @Bean
    public MessageChannel receiveRateChannel() {
        return new DirectChannel();
    }


    /**
     * Конечная точка выходного канала по получению данных о курсах валют
     */

    @Bean
    @ServiceActivator(inputChannel = "receiveRateChannel")
    public MessageHandler receiveRateHandler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                log.info("Handling message: " + message);
                if (message.getPayload() instanceof CurrencyRateData) {
                    ApplicationContextProvider.getIntegrationController().processRateData(
                            (CurrencyRateData) message.getPayload()
                    );
                } else {
                    log.error("Incorrect message type, expected: " + CurrencyRateData.class);
                }
            }
        };
    }

}

package com.antonch.earningscounter;

import com.antonch.earningscounter.common.Currency;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.LinkedMultiValueMap;

/**
 * Фабрика, инкапсулирующая создание сообщений
 * для запроса курсов валют за текущую или
 * прошлую дату
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
public class MessageFactory {

    private static final String SERVICE_TYPE_LATEST = "latest";

    public static Message REQUEST_LATEST_RATE_MESSAGE = newMessage(SERVICE_TYPE_LATEST, Currency.EUR, Currency.RUB);

    private MessageFactory() {
    }

    public static Message newMessage(String service) {
        return newMessage(service, Currency.EUR, Currency.RUB);
    }

    private static Message newMessage(String service, String currentRate, String targetRate) {
        return MessageBuilder
                .withPayload(
                        new LinkedMultiValueMap<String, String>() {
                            {
                                add("base", currentRate);
                                add("symbols", targetRate);
                            }
                        })
                .setHeader("service", service)
                .build();
    }

}

package com.antonch.earningscounter;

import com.antonch.earningscounter.conf.IntegrationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Проверка работоспособности шлюза
 *
 * @author anton.chekhlenkov@gmail.com
 * @since 11.12.2018
 */
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = IntegrationConfig.class)
//@EnableIntegration
public class HttpOutboundGatewayTest {

//    @Autowired
//    MessageChannel requestRateChannel;
//
//    @Autowired
//    PollableChannel receiveRateChannel;
//
//
//    @Test
//    public void testRequestRate() {
//
//        Message msg = MessageBuilder
//                .withPayload("")
//                .setHeader("service", "")
//                .setHeader("access_key", "")
//                .build();
//        requestRateChannel.send(msg);
//        Message<?> message = receiveRateChannel.receive(1000);
//
//        assertThat(message, is(notNullValue()));
//        assertThat(message.getPayload(), is(notNullValue()));
//    }

}
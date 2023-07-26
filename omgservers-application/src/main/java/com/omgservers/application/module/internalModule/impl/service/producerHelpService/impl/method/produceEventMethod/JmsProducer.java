package com.omgservers.application.module.internalModule.impl.service.producerHelpService.impl.method.produceEventMethod;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class JmsProducer {

    final JMSProducer producer;
    final JMSContext context;
    final Queue queue;

    public JmsProducer(final ConnectionFactory connectionFactory, final String queueName) {
        context = connectionFactory.createContext();
        queue = context.createQueue(queueName);
        producer = context.createProducer();
        log.info("Producer was created, queueName={}", queueName);
    }

    public void send(String text, Long group) throws JMSException {
        final var message = context.createTextMessage(text);
        message.setStringProperty("JMSXGroupID", group.toString());
        producer.send(queue, message);
    }
}

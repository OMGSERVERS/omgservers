package com.omgservers.application.module.internalModule.impl.service.consumerHelpService.impl.method.startConsumersMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.HandlerHelpService;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.request.HandleEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.response.HandleEventHelpResponse;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
class JmsConsumer implements Runnable {
    static final long INTERVAL_BEFORE_RECONNECT = 1000;

    final HandlerHelpService handlerHelpService;

    final ConnectionFactory connectionFactory;
    final AtomicInteger consumerCounter;
    final ObjectMapper objectMapper;
    final String queueName;

    public JmsConsumer(final HandlerHelpService handlerHelpService,
                       final ConnectionFactory connectionFactory,
                       final ObjectMapper objectMapper,
                       final String queueName) {
        this.handlerHelpService = handlerHelpService;
        this.consumerCounter = new AtomicInteger();
        this.connectionFactory = connectionFactory;
        this.objectMapper = objectMapper;
        this.queueName = queueName;
    }

    @Override
    @WithSpan
    public void run() {
        Thread.currentThread().setName("omgservers-event-consumer-" + consumerCounter.incrementAndGet());

        while (!Thread.interrupted()) {
            try (final var context = connectionFactory.createContext(JMSContext.SESSION_TRANSACTED)) {
                try (final var consumer = context.createConsumer(context.createQueue(queueName))) {
                    log.info("Consumer was created, queue={}", queueName);
                    while (!Thread.currentThread().isInterrupted()) {
                        final var jmsMessage = consumer.receive();
                        final var messageBody = jmsMessage.getBody(String.class);

                        final EventModel event;
                        try {
                            event = objectMapper.readValue(messageBody, EventModel.class);
                            log.info("Event was consumed, event={}", event);
                        } catch (IOException e) {
                            log.error("Event is wrong, {}", e.getMessage());
                            context.commit();
                            continue;
                        }

                        final var request = new HandleEventHelpRequest(event);
                        handlerHelpService.handleEvent(request)
                                .map(HandleEventHelpResponse::getResult)
                                .invoke(result -> {
                                    if (result) {
                                        context.commit();
                                    } else {
                                        context.rollback();
                                    }
                                })
                                .await().atMost(Duration.ofSeconds(10));
                    }
                }
            } catch (Exception e) {
                log.error("Consumer failed, {}", e.getMessage());
                try {
                    Thread.sleep(INTERVAL_BEFORE_RECONNECT);
                    continue;
                } catch (InterruptedException e2) {
                    log.error("Consumer was interrupted, {}", e.getMessage());
                    break;
                }
            }

            log.info("Consumer closed");
        }

        log.info("Consumer finished");
    }
}

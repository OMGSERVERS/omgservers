package com.omgservers.application.module.internalModule.impl.service.consumerHelpService.impl.method.startConsumersMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.HandlerHelpService;
import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ApplicationScoped
class StartConsumersMethodImpl implements StartConsumersMethod {

    final HandlerHelpService handlerHelpService;

    final GetConfigOperation getConfigOperation;

    final ConnectionFactory connectionFactory;

    final ExecutorService executorService;
    final AtomicInteger consumerCounter;
    final ObjectMapper objectMapper;

    public StartConsumersMethodImpl(final HandlerHelpService handlerHelpService,
                                    final GetConfigOperation getConfigOperation,
                                    final ConnectionFactory connectionFactory,
                                    final ObjectMapper objectMapper) {
        this.handlerHelpService = handlerHelpService;
        this.getConfigOperation = getConfigOperation;
        this.connectionFactory = connectionFactory;
        this.objectMapper = objectMapper;
        executorService = Executors.newFixedThreadPool(getConfigOperation.getConfig().consumerCount());
        consumerCounter = new AtomicInteger();
    }

    @Override
    public Uni<Void> startConsumers() {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> {
                    if (getConfigOperation.getConfig().disableConsumers()) {
                        log.warn("Consumers were disabled, skip method");
                    } else {
                        final var queueName = getConfigOperation.getConfig().consumerQueue();
                        final var jmsConsumer = new JmsConsumer(handlerHelpService, connectionFactory, objectMapper, queueName);
                        final var consumerCount = getConfigOperation.getConfig().consumerCount();
                        for (int i = 0; i < consumerCount; i++) {
                            executorService.submit(jmsConsumer);
                        }
                        log.info("Consumers were submitted, count={}", consumerCount);
                        executorService.shutdown();
                    }
                });
    }
}

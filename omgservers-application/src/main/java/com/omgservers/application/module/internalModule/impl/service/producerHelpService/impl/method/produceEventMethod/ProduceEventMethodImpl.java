package com.omgservers.application.module.internalModule.impl.service.producerHelpService.impl.method.produceEventMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.module.internalModule.impl.service.producerHelpService.request.ProducerEventHelpRequest;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ApplicationScoped
class ProduceEventMethodImpl implements ProduceEventMethod {

    final ThreadLocal<JmsProducer> producers;
    final ObjectMapper objectMapper;
    final Executor executor;

    public ProduceEventMethodImpl(final ConnectionFactory connectionFactory,
                                  final GetConfigOperation getConfigOperation,
                                  final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        final var queueName = getConfigOperation.getConfig().producerQueue();
        producers = ThreadLocal.withInitial(() -> new JmsProducer(connectionFactory, queueName));
        final var producerCount = getConfigOperation.getConfig().producerCount();
        final var producerCounter = new AtomicInteger();
        executor = Executors.newFixedThreadPool(producerCount, runnable -> {
            final var thread = new Thread(runnable);
            thread.setName("omgservers-event-producer-" + producerCounter.incrementAndGet());
            return thread;
        });
    }

    @Override
    public Uni<Void> produceEventAsync(ProducerEventHelpRequest request) {
        ProducerEventHelpRequest.validate(request);

        final var event = request.getEvent();
        return Uni.createFrom().voidItem()
                .emitOn(executor)
                .invoke(voidItem -> produceEvent(event))
                .replaceWithVoid();
    }

    @Override
    public void produceEventSync(ProducerEventHelpRequest request) {
        ProducerEventHelpRequest.validate(request);

        final var event = request.getEvent();
        produceEvent(event);
    }

    void produceEvent(EventModel event) {
        final String text;
        try {
            text = objectMapper.writeValueAsString(event);
        } catch (IOException e) {
            log.error("Event is wrong, event={}, {}", event, e.getMessage());
            throw new ServerSideBadRequestException("event is wrong, " + e.getMessage());
        }

        try {
            final var producer = producers.get();
            producer.send(text, event.getGroup());
            log.info("Event was produced, event={}", event);
        } catch (Exception e) {
            log.error("producer failed, {}", e.getMessage());
            throw new ServerSideInternalException("producer failed, " + e.getMessage());
        }
    }
}

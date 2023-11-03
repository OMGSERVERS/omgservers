package com.omgservers.module.system.impl.bootstrap;

import com.omgservers.model.dto.system.GetEventRequest;
import com.omgservers.model.dto.system.GetEventResponse;
import com.omgservers.model.dto.system.HandleEventRequest;
import com.omgservers.model.dto.system.HandleEventResponse;
import com.omgservers.model.dto.system.UpdateEventsStatusRequest;
import com.omgservers.model.dto.system.UpdateEventsStatusResponse;
import com.omgservers.exception.ServerSideClientExceptionException;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.module.system.SystemModule;
import com.omgservers.operation.getConfig.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.Collections;
import java.util.stream.IntStream;

@Slf4j
@ApplicationScoped
public class BootstrapEventHandler {

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    final Multi<Message<Long>> inboxEvents;

    public BootstrapEventHandler(SystemModule systemModule,
                                 GetConfigOperation getConfigOperation,
                                 @Channel("inbox-events") Multi<Message<Long>> inboxEvents) {
        this.systemModule = systemModule;
        this.getConfigOperation = getConfigOperation;
        this.inboxEvents = inboxEvents;
    }

    @WithSpan
    void startup(@Observes @Priority(3000) StartupEvent event) {
        final var disableEventHandler = getConfigOperation.getConfig().disableEventHandler();
        if (disableEventHandler) {
            log.warn("Event handler was disabled, skip operation");
        } else {
            final var eventHandlerConcurrency = getConfigOperation.getConfig().eventHandlerConcurrency();
            IntStream.range(0, eventHandlerConcurrency).forEach(this::createEventHandler);
        }
    }

    void createEventHandler(int index) {
        inboxEvents.onItem().transformToUniAndConcatenate(message -> {
                    final var eventId = message.getPayload();
                    return handleEvent(eventId)
                            .call(voidItem -> Uni.createFrom().completionStage(message.ack()))
                            .onFailure()
                            .recoverWithUni(t -> {
                                log.warn("Handling of event failed, handler={}, eventId={}, {}",
                                        index, eventId, t.getMessage());
                                return Uni.createFrom().completionStage(message.nack(t));
                            })
                            .replaceWith(eventId);
                })
                .subscribe()
                .with(eventId -> log.debug("Event was handled, id={}", eventId),
                        t -> log.error("Event handler failed, {}", t.getMessage()));
    }

    Uni<Void> handleEvent(Long eventId) {
        return getEvent(eventId)
                .flatMap(event -> handleEvent(event)
                        .call(result -> {
                            if (result) {
                                return updateStatus(eventId, EventStatusEnum.PROCESSED);
                            } else {
                                return updateStatus(eventId, EventStatusEnum.FAILED);
                            }
                        })
                        .onFailure(ServerSideClientExceptionException.class)
                        .recoverWithUni(t -> {
                            log.error("Event handling failed, event was marked as failed, " +
                                            "eventId={}, " +
                                            "qualifier={}, " +
                                            "{}:{}",
                                    eventId,
                                    event.getQualifier(),
                                    t.getClass().getSimpleName(),
                                    t.getMessage());
                            return updateStatus(eventId, EventStatusEnum.FAILED);
                        })
                )
                .replaceWithVoid();
    }

    Uni<EventModel> getEvent(final Long eventId) {
        final var request = new GetEventRequest(eventId);
        return systemModule.getEventService().getEvent(request)
                .map(GetEventResponse::getEvent);
    }

    Uni<Boolean> handleEvent(EventModel event) {
        final var request = new HandleEventRequest(event);
        return systemModule.getHandlerService().handleEvent(request)
                .map(HandleEventResponse::getResult);
    }

    Uni<Boolean> updateStatus(Long id, EventStatusEnum status) {
        final var request = new UpdateEventsStatusRequest(Collections.singletonList(id), status);
        return systemModule.getEventService().updateEventsStatus(request)
                .map(UpdateEventsStatusResponse::getUpdated);
    }
}

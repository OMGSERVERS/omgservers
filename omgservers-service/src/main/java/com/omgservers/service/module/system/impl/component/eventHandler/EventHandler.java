package com.omgservers.service.module.system.impl.component.eventHandler;

import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.service.exception.ServerSideClientExceptionException;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class EventHandler {

    final SystemModule systemModule;

    final GetConfigOperation getConfigOperation;

    @Incoming("inbox-events")
    @Blocking(ordered = false)
    Uni<Void> eventConsumer(Message<Long> message) {
        final var eventId = message.getPayload();
        return handleEvent(eventId)
                .call(voidItem -> Uni.createFrom().completionStage(message.ack()))
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Handling of event failed, eventId={}, {}", eventId, t.getMessage());
                    return Uni.createFrom().completionStage(message.nack(t));
                });
    }

    Uni<Void> handleEvent(final Long eventId) {
        return systemModule.getShortcutService().getEvent(eventId)
                .flatMap(event -> systemModule.getShortcutService().handleEvent(event)
                        .call(result -> {
                            if (result) {
                                return systemModule.getShortcutService()
                                        .updateEventStatus(eventId, EventStatusEnum.PROCESSED);
                            } else {
                                return systemModule.getShortcutService()
                                        .updateEventStatus(eventId, EventStatusEnum.FAILED);
                            }
                        })
                        .onFailure(ServerSideClientExceptionException.class)
                        .recoverWithUni(t -> {
                            log.error("Event handling failed, marked as failed, " +
                                            "eventId={}, " +
                                            "qualifier={}, " +
                                            "{}:{}",
                                    eventId,
                                    event.getQualifier(),
                                    t.getClass().getSimpleName(),
                                    t.getMessage());
                            return systemModule.getShortcutService()
                                    .updateEventStatus(eventId, EventStatusEnum.FAILED);
                        })
                )
                .replaceWithVoid();
    }
}

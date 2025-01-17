package com.omgservers.service.handler.impl.queue;

import com.omgservers.schema.model.queue.QueueModel;
import com.omgservers.schema.module.queue.queue.GetQueueRequest;
import com.omgservers.schema.module.queue.queue.GetQueueResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.queue.QueueCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.queue.QueueModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class QueueCreatedEventHandlerImpl implements EventHandler {

    final QueueModule queueModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.QUEUE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (QueueCreatedEventBodyModel) event.getBody();
        final var queueId = body.getId();

        return getQueue(queueId)
                .flatMap(queue -> {
                    log.debug("Created, {}", queue);
                    return Uni.createFrom().voidItem();
                });
    }

    Uni<QueueModel> getQueue(final Long id) {
        final var request = new GetQueueRequest(id);
        return queueModule.getQueueService().execute(request)
                .map(GetQueueResponse::getQueue);
    }
}

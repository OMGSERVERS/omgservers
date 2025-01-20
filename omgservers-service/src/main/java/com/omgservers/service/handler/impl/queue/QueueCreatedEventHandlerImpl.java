package com.omgservers.service.handler.impl.queue;

import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.schema.model.queue.QueueModel;
import com.omgservers.schema.module.queue.queue.GetQueueRequest;
import com.omgservers.schema.module.queue.queue.GetQueueResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.queue.QueueCreatedEventBodyModel;
import com.omgservers.service.factory.system.JobModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.queue.QueueShard;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.SyncJobRequest;
import com.omgservers.service.service.job.dto.SyncJobResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class QueueCreatedEventHandlerImpl implements EventHandler {

    final QueueShard queueShard;

    final JobService jobService;

    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.QUEUE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (QueueCreatedEventBodyModel) event.getBody();
        final var queueId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getQueue(queueId)
                .flatMap(queue -> {
                    log.debug("Created, {}", queue);

                    return syncJob(queueId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<QueueModel> getQueue(final Long id) {
        final var request = new GetQueueRequest(id);
        return queueShard.getQueueService().execute(request)
                .map(GetQueueResponse::getQueue);
    }

    Uni<Boolean> syncJob(final Long queueId,
                         final String idempotencyKey) {
        final var job = jobModelFactory.create(JobQualifierEnum.QUEUE,
                queueId,
                queueId,
                idempotencyKey);

        final var request = new SyncJobRequest(job);
        return jobService.syncJobWithIdempotency(request)
                .map(SyncJobResponse::getCreated);
    }
}

package com.omgservers.service.handler.job.pool;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.job.PoolJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.system.SystemModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolJobTaskExecutionRequestedEventHandlerImpl implements EventHandler {

    static private final int JOB_INTERVAL_IN_SECONDS = 1;

    final PoolJobTaskImpl poolJobTask;
    final SystemModule systemModule;

    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_JOB_TASK_EXECUTION_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolJobTaskExecutionRequestedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();

        return poolJobTask.executeTask(poolId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, poolId={}, {}:{}",
                            poolId, t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(Boolean.TRUE);
                })
                .flatMap(oneMoreTime -> {
                    if (oneMoreTime) {
                        return requestFurtherExecution(poolId);
                    } else {
                        log.warn("Job was finished");
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<Void> requestFurtherExecution(final Long poolId) {
        final var eventBody = new PoolJobTaskExecutionRequestedEventBodyModel(poolId);
        final var eventModel = eventModelFactory.create(eventBody);
        eventModel.setDelayed(Instant.now().plus(Duration.ofSeconds(JOB_INTERVAL_IN_SECONDS)));

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(syncEventRequest)
                .replaceWithVoid();
    }
}

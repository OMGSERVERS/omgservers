package com.omgservers.service.handler.job;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.StageJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.handler.job.task.stage.StageJobTaskImpl;
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
public class StageJobTaskExecutionRequestedEventHandlerImpl implements EventHandler {

    static private final int JOB_INTERVAL_IN_SECONDS = 60;

    final StageJobTaskImpl stageJobTaskImpl;
    final SystemModule systemModule;

    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STAGE_JOB_TASK_EXECUTION_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (StageJobTaskExecutionRequestedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var stageId = body.getStageId();

        return stageJobTaskImpl.executeTask(tenantId, stageId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(Boolean.TRUE);
                })
                .flatMap(oneMoreTime -> {
                    if (oneMoreTime) {
                        return requestFurtherExecution(tenantId, stageId);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<Void> requestFurtherExecution(final Long tenantId, final Long stageId) {
        final var eventBody = new StageJobTaskExecutionRequestedEventBodyModel(tenantId, stageId);
        final var eventModel = eventModelFactory.create(eventBody);
        eventModel.setDelayed(Instant.now().plus(Duration.ofSeconds(JOB_INTERVAL_IN_SECONDS)));

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEvent(syncEventRequest)
                .replaceWithVoid();
    }
}

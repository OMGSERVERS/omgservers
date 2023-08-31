package com.omgservers.handler;

import com.omgservers.dto.internal.SyncJobShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.module.internal.factory.JobModelFactory;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerCreatedEventBodyModel;
import com.omgservers.model.job.JobModel;
import com.omgservers.model.job.JobType;
import com.omgservers.model.stage.StageModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final InternalModule internalModule;

    final GenerateIdOperation generateIdOperation;

    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchmakerCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var stageId = body.getStageId();
        final var id = body.getId();
        return setMatchmakerId(tenantId, stageId, id)
                .flatMap(stage -> syncJob(id, id))
                .replaceWith(true);
    }

    Uni<StageModel> setMatchmakerId(Long tenantId, Long stageId, Long matchmakerId) {
        final var getStageShardedRequest = new GetStageShardedRequest(tenantId, stageId);
        return tenantModule.getStageShardedService().getStage(getStageShardedRequest)
                .map(GetStageShardedResponse::getStage)
                .flatMap(stage -> {
                    stage.setMatchmakerId(matchmakerId);
                    final var syncStageShardedRequest = new SyncStageShardedRequest(tenantId, stage);
                    return tenantModule.getStageShardedService().syncStage(syncStageShardedRequest)
                            .replaceWith(stage);
                });
    }

    Uni<JobModel> syncJob(Long shardKey, Long entityId) {
        final var job = jobModelFactory.create(shardKey, entityId, JobType.MATCHMAKER);
        final var request = new SyncJobShardedRequest(job);
        return internalModule.getJobShardedService().syncJob(request)
                .replaceWith(job);
    }
}

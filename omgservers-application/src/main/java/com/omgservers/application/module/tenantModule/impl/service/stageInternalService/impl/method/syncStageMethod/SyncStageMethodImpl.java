package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStageMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStageOperation.UpsertStageOperation;
import com.omgservers.application.module.tenantModule.impl.operation.validateStageOperation.ValidateStageOperation;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStageInternalRequest;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncStageMethodImpl implements SyncStageMethod {

    final InternalModule internalModule;

    final ValidateStageOperation validateStageOperation;
    final UpsertStageOperation upsertStageOperation;
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<Void> syncStage(SyncStageInternalRequest request) {
        SyncStageInternalRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stage = request.getStage();
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> validateStageOperation.validateStage(stage))
                .flatMap(validatedStage -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> syncStage(shardModel.shard(), tenantId, stage));
    }

    Uni<Void> syncStage(Integer shard, Long tenantId, StageModel stage) {
        return pgPool.withTransaction(sqlConnection ->
                        upsertStageOperation.upsertStage(sqlConnection, shard, stage)
                                .call(inserted -> {
                                    if (inserted) {
                                        final var id = stage.getId();
                                        final var eventBody = new StageCreatedEventBodyModel(tenantId, id);
                                        final var insertEventInternalRequest =
                                                new InsertEventHelpRequest(sqlConnection, eventBody);
                                        return internalModule.getEventHelpService()
                                                .insertEvent(insertEventInternalRequest);
                                    } else {
                                        return Uni.createFrom().voidItem();
                                    }
                                })
                                .call(inserted -> {
                                    final LogModel syncLog;
                                    if (inserted) {
                                        syncLog = logModelFactory.create("Stage was created, stage=" + stage);
                                    } else {
                                        syncLog = logModelFactory.create("Stage was update, stage=" + stage);
                                    }
                                    final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                                    return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                                }))
                .replaceWithVoid();
    }
}

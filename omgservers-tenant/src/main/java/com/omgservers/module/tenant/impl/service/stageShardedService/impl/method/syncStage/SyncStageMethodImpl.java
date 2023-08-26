package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStage;

import com.omgservers.module.tenant.impl.operation.validateStage.ValidateStageOperation;
import com.omgservers.module.tenant.impl.operation.upsertStage.UpsertStageOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.tenantModule.SyncStageShardRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.event.body.StageUpdatedEventBodyModel;
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

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncStageInternalResponse> syncStage(SyncStageShardRequest request) {
        SyncStageShardRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stage = request.getStage();
        validateStageOperation.validateStage(stage);
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> upsertStageOperation
                                .upsertStage(sqlConnection, shardModel.shard(), stage),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create("Stage was created, stage=" + stage);
                            } else {
                                return logModelFactory.create("Stage was updated, stage=" + stage);
                            }
                        },
                        inserted -> {
                            final var id = stage.getId();
                            if (inserted) {
                                return new StageCreatedEventBodyModel(tenantId, id);
                            } else {
                                return new StageUpdatedEventBodyModel(tenantId, id);
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(SyncStageInternalResponse::new);
    }
}

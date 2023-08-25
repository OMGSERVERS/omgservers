package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStageMethod;

import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.InternalModule;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStageOperation.UpsertStageOperation;
import com.omgservers.application.module.tenantModule.impl.operation.validateStageOperation.ValidateStageOperation;
import com.omgservers.base.impl.operation.changeOperation.ChangeOperation;
import com.omgservers.dto.tenantModule.SyncStageInternalRequest;
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
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncStageInternalResponse> syncStage(SyncStageInternalRequest request) {
        SyncStageInternalRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stage = request.getStage();
        validateStageOperation.validateStage(stage);
        return changeOperation.changeWithEvent(request,
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
                )
                .map(SyncStageInternalResponse::new);
    }
}

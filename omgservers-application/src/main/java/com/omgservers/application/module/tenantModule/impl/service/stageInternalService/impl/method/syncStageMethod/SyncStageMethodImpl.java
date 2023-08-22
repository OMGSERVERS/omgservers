package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStageMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStageOperation.UpsertStageOperation;
import com.omgservers.application.module.tenantModule.impl.operation.validateStageOperation.ValidateStageOperation;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStageInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
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
                                return new StageCreatedEventBodyModel(tenantId, id);
                            }
                        }
                )
                .map(SyncStageInternalResponse::new);
    }
}

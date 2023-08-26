package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.deleteStage;

import com.omgservers.module.tenant.impl.operation.deleteStage.DeleteStageOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.tenantModule.DeleteStageShardRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.model.event.body.StageDeletedEventBodyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteStageMethodImpl implements DeleteStageMethod {

    final InternalModule internalModule;

    final DeleteStageOperation deleteStageOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteStageInternalResponse> deleteStage(final DeleteStageShardRequest request) {
        DeleteStageShardRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        ((sqlConnection, shardModel) -> deleteStageOperation
                                .deleteStage(sqlConnection, shardModel.shard(), id)),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create(String.format("Stage was deleted, " +
                                        "tenantId=%d, id=%d", tenantId, id));
                            } else {
                                return null;
                            }
                        },
                        deleted -> {
                            if (deleted) {
                                return new StageDeletedEventBodyModel(tenantId, id);
                            } else {
                                return null;
                            }
                        }))
                .map(ChangeWithEventResponse::getResult)
                .map(DeleteStageInternalResponse::new);
    }
}

package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.deleteStageMethod;

import com.omgservers.application.module.internalModule.model.event.body.StageDeletedEventBodyModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.deleteStageOperation.DeleteStageOperation;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.DeleteStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.DeleteStageInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteStageMethodImpl implements DeleteStageMethod {

    final DeleteStageOperation deleteStageOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteStageInternalResponse> deleteStage(final DeleteStageInternalRequest request) {
        DeleteStageInternalRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return changeOperation.changeWithEvent(request,
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
                        })
                .map(DeleteStageInternalResponse::new);
    }
}

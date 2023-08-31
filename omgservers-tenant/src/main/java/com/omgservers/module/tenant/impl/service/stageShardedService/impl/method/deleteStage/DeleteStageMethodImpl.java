package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.deleteStage;

import com.omgservers.module.tenant.impl.operation.deleteStage.DeleteStageOperation;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import com.omgservers.dto.tenant.DeleteStageShardedRequest;
import com.omgservers.dto.tenant.DeleteStageShardedResponse;
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
    public Uni<DeleteStageShardedResponse> deleteStage(final DeleteStageShardedRequest request) {
        DeleteStageShardedRequest.validate(request);

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
                .map(DeleteStageShardedResponse::new);
    }
}

package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteRuntimeMethod;

import com.omgservers.application.module.runtimeModule.impl.operation.deleteRuntimeOperation.DeleteRuntimeOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.dto.runtimeModule.DeleteRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import com.omgservers.model.event.body.RuntimeDeletedEventBodyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimeMethodImpl implements DeleteRuntimeMethod {

    final InternalModule internalModule;

    final DeleteRuntimeOperation deleteRuntimeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeShardRequest request) {
        DeleteRuntimeShardRequest.validate(request);

        final var id = request.getId();
        return internalModule.getChangeService().changeWithEvent(new ChangeWithEventRequest(request,
                        (sqlConnection, shardModel) -> deleteRuntimeOperation
                                .deleteRuntime(sqlConnection, shardModel.shard(), id),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create("Runtime was deleted, id=" + id);
                            } else {
                                return null;
                            }
                        },
                        deleted -> {
                            if (deleted) {
                                return new RuntimeDeletedEventBodyModel(id);
                            } else {
                                return null;
                            }
                        }
                ))
                .map(ChangeWithEventResponse::getResult)
                .map(DeleteRuntimeInternalResponse::new);
    }
}

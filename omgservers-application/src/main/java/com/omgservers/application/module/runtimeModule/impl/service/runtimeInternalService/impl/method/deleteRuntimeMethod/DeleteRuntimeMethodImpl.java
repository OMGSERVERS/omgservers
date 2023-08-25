package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteRuntimeMethod;

import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.InternalModule;
import com.omgservers.application.module.runtimeModule.impl.operation.deleteRuntimeOperation.DeleteRuntimeOperation;
import com.omgservers.base.impl.operation.changeOperation.ChangeOperation;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalRequest;
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
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeInternalRequest request) {
        DeleteRuntimeInternalRequest.validate(request);

        final var id = request.getId();
        return changeOperation.changeWithEvent(request,
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
                )
                .map(DeleteRuntimeInternalResponse::new);
    }
}

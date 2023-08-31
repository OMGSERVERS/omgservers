package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.deleteRuntime;

import com.omgservers.module.runtime.impl.operation.deleteRuntime.DeleteRuntimeOperation;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internal.ChangeWithEventRequest;
import com.omgservers.dto.internal.ChangeWithEventResponse;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeShardedResponse;
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
    public Uni<DeleteRuntimeShardedResponse> deleteRuntime(DeleteRuntimeShardedRequest request) {
        DeleteRuntimeShardedRequest.validate(request);

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
                .map(DeleteRuntimeShardedResponse::new);
    }
}

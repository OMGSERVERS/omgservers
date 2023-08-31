package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.deleteRuntimeCommand;

import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.runtime.impl.operation.deleteRuntimeCommand.DeleteRuntimeCommandOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimeCommandMethodImpl implements DeleteRuntimeCommandMethod {

    final InternalModule internalModule;

    final DeleteRuntimeCommandOperation deleteRuntimeCommandOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteRuntimeCommandShardedResponse> deleteRuntimeCommand(DeleteRuntimeCommandShardedRequest request) {
        DeleteRuntimeCommandShardedRequest.validate(request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        (sqlConnection, shardModel) -> deleteRuntimeCommandOperation
                                .deleteRuntimeCommand(sqlConnection, shardModel.shard(), id),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create(String.format("Runtime command was deleted, " +
                                        "runtimeId=%d, id=%d", runtimeId, id));
                            } else {
                                return null;
                            }
                        }
                ))
                .map(ChangeWithLogResponse::getResult)
                .map(DeleteRuntimeCommandShardedResponse::new);
    }
}

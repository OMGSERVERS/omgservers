package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.deleteCommand;

import com.omgservers.module.runtime.impl.operation.deleteCommand.DeleteCommandOperation;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internal.ChangeWithLogRequest;
import com.omgservers.dto.internal.ChangeWithLogResponse;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteCommandMethodImpl implements DeleteCommandMethod {

    final InternalModule internalModule;

    final DeleteCommandOperation deleteCommandOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteRuntimeCommandShardedResponse> deleteCommand(DeleteRuntimeCommandShardedRequest request) {
        DeleteRuntimeCommandShardedRequest.validate(request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        (sqlConnection, shardModel) -> deleteCommandOperation
                                .deleteCommand(sqlConnection, shardModel.shard(), id),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create(String.format("Command was deleted, " +
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

package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteCommandMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.runtimeModule.impl.operation.deleteCommandOperation.DeleteCommandOperation;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteCommandInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteCommandInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
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
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandInternalRequest request) {
        DeleteCommandInternalRequest.validate(request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return changeOperation.changeWithLog(request,
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
                )
                .map(DeleteCommandInternalResponse::new);
    }
}

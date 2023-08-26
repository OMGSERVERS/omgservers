package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteCommandMethod;

import com.omgservers.application.module.runtimeModule.impl.operation.deleteCommandOperation.DeleteCommandOperation;
import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.dto.runtimeModule.DeleteCommandRoutedRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
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
    public Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandRoutedRequest request) {
        DeleteCommandRoutedRequest.validate(request);

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
                .map(DeleteCommandInternalResponse::new);
    }
}

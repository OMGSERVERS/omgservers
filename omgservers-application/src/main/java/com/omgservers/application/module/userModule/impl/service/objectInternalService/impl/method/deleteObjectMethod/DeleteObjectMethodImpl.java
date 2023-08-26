package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.deleteObjectMethod;

import com.omgservers.application.module.userModule.impl.operation.deleteObjectOperation.DeleteObjectOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.dto.userModule.DeleteObjectShardRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteObjectMethodImpl implements DeleteObjectMethod {

    final InternalModule internalModule;

    final DeleteObjectOperation deleteObjectOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteObjectInternalResponse> deleteObject(final DeleteObjectShardRequest request) {
        DeleteObjectShardRequest.validate(request);

        final var userId = request.getUserId();
        final var id = request.getId();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        (sqlConnection, shardModel) -> deleteObjectOperation
                                .deleteObject(sqlConnection, shardModel.shard(), id),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create(String.format("Object was deleted, " +
                                        "userId=%d, id=%d", userId, id));
                            } else {
                                return null;
                            }
                        }))
                .map(ChangeWithLogResponse::getResult)
                .map(DeleteObjectInternalResponse::new);
    }
}

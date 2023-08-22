package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.deleteObjectMethod;

import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.userModule.impl.operation.deleteObjectOperation.DeleteObjectOperation;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.DeleteObjectInternalRequest;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.response.DeleteObjectInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteObjectMethodImpl implements DeleteObjectMethod {

    final DeleteObjectOperation deleteObjectOperation;
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<DeleteObjectInternalResponse> deleteObject(final DeleteObjectInternalRequest request) {
        DeleteObjectInternalRequest.validate(request);

        final var userId = request.getUserId();
        final var id = request.getId();
        return changeOperation.changeWithLog(request,
                        (sqlConnection, shardModel) -> deleteObjectOperation
                                .deleteObject(sqlConnection, shardModel.shard(), id),
                        deleted -> {
                            if (deleted) {
                                return logModelFactory.create(String.format("Object was deleted, " +
                                        "userId=%d, id=%d", userId, id));
                            } else {
                                return null;
                            }
                        })
                .map(DeleteObjectInternalResponse::new);
    }
}

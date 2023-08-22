package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.deleteObjectMethod;

import com.omgservers.application.module.userModule.impl.operation.deleteObjectOperation.DeleteObjectOperation;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.request.DeleteObjectInternalRequest;
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

    final PgPool pgPool;

    @Override
    public Uni<Void> deleteObject(final DeleteObjectInternalRequest request) {
        DeleteObjectInternalRequest.validate(request);

        final var id = request.getId();
        return changeOperation.change(request,
                        ((sqlConnection, shardModel) -> deleteObjectOperation
                                .deleteObject(sqlConnection, shardModel.shard(), id)))
                // TODO: implement response with deleted flag
                .replaceWithVoid();
    }
}

package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.deleteAttributeMethod;

import com.omgservers.application.module.userModule.impl.operation.deleteAttributeOperation.DeleteAttributeOperation;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.DeleteAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.DeleteAttributeInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteAttributeMethodImpl implements DeleteAttributeMethod {

    final DeleteAttributeOperation deleteAttributeOperation;
    final ChangeOperation changeOperation;
    final PgPool pgPool;

    @Override
    public Uni<DeleteAttributeInternalResponse> deleteAttribute(final DeleteAttributeInternalRequest request) {
        DeleteAttributeInternalRequest.validate(request);

        final var player = request.getPlayerId();
        final var name = request.getName();
        return changeOperation.change(request,
                        ((sqlConnection, shardModel) -> deleteAttributeOperation
                                .deleteAttribute(sqlConnection, shardModel.shard(), player, name)))
                .map(DeleteAttributeInternalResponse::new);
    }
}

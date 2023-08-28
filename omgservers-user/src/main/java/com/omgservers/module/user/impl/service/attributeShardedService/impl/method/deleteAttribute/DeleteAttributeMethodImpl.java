package com.omgservers.module.user.impl.service.attributeShardedService.impl.method.deleteAttribute;

import com.omgservers.dto.internal.ChangeRequest;
import com.omgservers.dto.internal.ChangeResponse;
import com.omgservers.dto.user.DeleteAttributeShardedResponse;
import com.omgservers.dto.user.DeleteAttributeShardedRequest;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.user.impl.operation.deleteAttribute.DeleteAttributeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteAttributeMethodImpl implements DeleteAttributeMethod {

    final InternalModule internalModule;

    final DeleteAttributeOperation deleteAttributeOperation;
    final PgPool pgPool;

    @Override
    public Uni<DeleteAttributeShardedResponse> deleteAttribute(final DeleteAttributeShardedRequest request) {
        DeleteAttributeShardedRequest.validate(request);

        final var player = request.getPlayerId();
        final var name = request.getName();
        final var changeRequest = new ChangeRequest(request,
                (sqlConnection, shardModel) -> deleteAttributeOperation
                        .deleteAttribute(sqlConnection, shardModel.shard(), player, name));
        return internalModule.getChangeService().change(changeRequest)
                .map(ChangeResponse::getResult)
                .map(DeleteAttributeShardedResponse::new);
    }
}

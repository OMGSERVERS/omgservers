package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.deleteAttributeMethod;

import com.omgservers.application.module.userModule.impl.operation.deleteAttributeOperation.DeleteAttributeOperation;
import com.omgservers.base.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeRequest;
import com.omgservers.dto.internalModule.ChangeResponse;
import com.omgservers.dto.userModule.DeleteAttributeRoutedRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
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
    public Uni<DeleteAttributeInternalResponse> deleteAttribute(final DeleteAttributeRoutedRequest request) {
        DeleteAttributeRoutedRequest.validate(request);

        final var player = request.getPlayerId();
        final var name = request.getName();
        final var changeRequest = new ChangeRequest(request,
                (sqlConnection, shardModel) -> deleteAttributeOperation
                        .deleteAttribute(sqlConnection, shardModel.shard(), player, name));
        return internalModule.getChangeService().change(changeRequest)
                .map(ChangeResponse::getResult)
                .map(DeleteAttributeInternalResponse::new);
    }
}

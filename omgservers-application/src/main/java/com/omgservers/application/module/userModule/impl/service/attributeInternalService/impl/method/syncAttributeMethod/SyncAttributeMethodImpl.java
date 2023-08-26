package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.syncAttributeMethod;

import com.omgservers.application.module.userModule.impl.operation.upsertAttributeOperation.UpsertAttributeOperation;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeRequest;
import com.omgservers.dto.internalModule.ChangeResponse;
import com.omgservers.dto.userModule.SyncAttributeShardRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncAttributeMethodImpl implements SyncAttributeMethod {

    final InternalModule internalModule;

    final UpsertAttributeOperation upsertAttributeOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeShardRequest request) {
        SyncAttributeShardRequest.validate(request);

        final var attribute = request.getAttribute();
        final var changeRequest = new ChangeRequest(request,
                (sqlConnection, shardModel) -> upsertAttributeOperation
                        .upsertAttribute(sqlConnection, shardModel.shard(), attribute));
        return internalModule.getChangeService().change(changeRequest)
                .map(ChangeResponse::getResult)
                .map(SyncAttributeInternalResponse::new);
    }
}

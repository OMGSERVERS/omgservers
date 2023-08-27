package com.omgservers.module.user.impl.service.attributeShardedService.impl.method.syncAttribute;

import com.omgservers.dto.internal.ChangeRequest;
import com.omgservers.dto.internal.ChangeResponse;
import com.omgservers.dto.user.SyncAttributeShardedResponse;
import com.omgservers.dto.user.SyncAttributeShardedRequest;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.user.impl.operation.upsertAttribute.UpsertAttributeOperation;
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
    public Uni<SyncAttributeShardedResponse> syncAttribute(SyncAttributeShardedRequest request) {
        SyncAttributeShardedRequest.validate(request);

        final var attribute = request.getAttribute();
        final var changeRequest = new ChangeRequest(request,
                (sqlConnection, shardModel) -> upsertAttributeOperation
                        .upsertAttribute(sqlConnection, shardModel.shard(), attribute));
        return internalModule.getChangeService().change(changeRequest)
                .map(ChangeResponse::getResult)
                .map(SyncAttributeShardedResponse::new);
    }
}

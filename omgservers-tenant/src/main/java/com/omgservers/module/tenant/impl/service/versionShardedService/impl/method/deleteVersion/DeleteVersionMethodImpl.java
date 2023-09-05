package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.deleteVersion;

import com.omgservers.ChangeContext;
import com.omgservers.dto.tenant.DeleteVersionShardedRequest;
import com.omgservers.dto.tenant.DeleteVersionShardedResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.tenant.impl.operation.deleteVersion.DeleteVersionOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteVersionMethodImpl implements DeleteVersionMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteVersionOperation deleteVersionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<DeleteVersionShardedResponse> deleteVersion(final DeleteVersionShardedRequest request) {
        DeleteVersionShardedRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, tenantId, id))
                .map(DeleteVersionShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long tenantId, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteVersionOperation.deleteVersion(changeContext, sqlConnection, shardModel.shard(), tenantId, id))
                .map(ChangeContext::getResult);
    }
}

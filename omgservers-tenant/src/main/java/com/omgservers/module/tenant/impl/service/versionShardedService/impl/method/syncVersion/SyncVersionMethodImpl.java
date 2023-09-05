package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.syncVersion;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.tenant.SyncVersionShardedRequest;
import com.omgservers.dto.tenant.SyncVersionShardedResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.module.tenant.impl.operation.upsertVersion.UpsertVersionOperation;
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
class SyncVersionMethodImpl implements SyncVersionMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertVersionOperation upsertVersionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request) {
        SyncVersionShardedRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var version = request.getVersion();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, tenantId, version))
                .map(SyncVersionShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long tenantId, VersionModel version) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertVersionOperation.upsertVersion(changeContext, sqlConnection, shardModel.shard(), tenantId, version))
                .map(ChangeContext::getResult);
    }
}

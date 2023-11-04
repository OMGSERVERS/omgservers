package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.deleteVersion;

import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.service.module.tenant.impl.operation.deleteVersion.DeleteVersionOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    public Uni<DeleteVersionResponse> deleteVersion(final DeleteVersionRequest request) {
        final var tenantId = request.getTenantId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, tenantId, id))
                .map(DeleteVersionResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long tenantId, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteVersionOperation.deleteVersion(changeContext, sqlConnection, shardModel.shard(), tenantId, id))
                .map(ChangeContext::getResult);
    }
}

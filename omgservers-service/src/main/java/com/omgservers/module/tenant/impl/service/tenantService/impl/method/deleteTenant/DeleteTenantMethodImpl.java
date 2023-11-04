package com.omgservers.module.tenant.impl.service.tenantService.impl.method.deleteTenant;

import com.omgservers.model.dto.tenant.DeleteTenantRequest;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.tenant.impl.operation.deleteTenant.DeleteTenantOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
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
class DeleteTenantMethodImpl implements DeleteTenantMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteTenantOperation deleteTenantOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<Void> deleteTenant(final DeleteTenantRequest request) {
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, id))
                //TODO implement response with deleted flag
                .replaceWithVoid();
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteTenantOperation.deleteTenant(changeContext, sqlConnection, shardModel.shard(), id))
                .map(ChangeContext::getResult);
    }
}

package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.deleteTenant;

import com.omgservers.model.dto.tenant.DeleteTenantRequest;
import com.omgservers.model.dto.tenant.DeleteTenantResponse;
import com.omgservers.service.module.tenant.impl.operation.tenant.deleteTenant.DeleteTenantOperation;
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
class DeleteTenantMethodImpl implements DeleteTenantMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteTenantOperation deleteTenantOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<DeleteTenantResponse> deleteTenant(final DeleteTenantRequest request) {
        log.debug("Delete tenant, request={}", request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deleteTenantOperation
                                        .deleteTenant(changeContext, sqlConnection, shardModel.shard(), id))
                        .map(ChangeContext::getResult))
                .map(DeleteTenantResponse::new);
    }
}

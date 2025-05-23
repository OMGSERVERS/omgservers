package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenant;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenant.DeleteTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.DeleteTenantResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenant.DeleteTenantOperation;
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

    final PgPool pgPool;

    @Override
    public Uni<DeleteTenantResponse> deleteTenant(final ShardModel shardModel,
                                                  final DeleteTenantRequest request) {
        log.debug("{}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deleteTenantOperation
                                .execute(changeContext, sqlConnection, shardModel.slot(), id))
                .map(ChangeContext::getResult)
                .map(DeleteTenantResponse::new);
    }
}

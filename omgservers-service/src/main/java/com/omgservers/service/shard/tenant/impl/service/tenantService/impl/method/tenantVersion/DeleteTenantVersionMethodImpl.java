package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantVersion;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantVersion.DeleteTenantVersionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantVersionMethodImpl implements DeleteTenantVersionMethod {

    final DeleteTenantVersionOperation deleteTenantVersionOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<DeleteTenantVersionResponse> execute(final ShardModel shardModel,
                                                    final DeleteTenantVersionRequest request) {
        log.trace("{}", request);
        final var tenantId = request.getTenantId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteTenantVersionOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                tenantId,
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteTenantVersionResponse::new);
    }
}

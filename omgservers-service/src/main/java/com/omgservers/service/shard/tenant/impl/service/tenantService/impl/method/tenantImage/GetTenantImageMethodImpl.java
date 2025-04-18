package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantImage.GetTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.GetTenantImageResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.SelectTenantImageOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetTenantImageMethodImpl implements GetTenantImageMethod {

    final SelectTenantImageOperation selectTenantImageOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetTenantImageResponse> execute(final ShardModel shardModel,
                                               final GetTenantImageRequest request) {
        log.trace("{}", request);

        final var slot = shardModel.slot();
        final var tenantId = request.getTenantId();
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectTenantImageOperation
                        .execute(sqlConnection, slot, tenantId, id))
                .map(GetTenantImageResponse::new);
    }
}

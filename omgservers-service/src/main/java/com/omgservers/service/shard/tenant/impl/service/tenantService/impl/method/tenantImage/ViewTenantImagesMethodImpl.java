package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.shard.tenant.tenantImage.ViewTenantImagesResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.SelectActiveTenantImageByTenantIdOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.SelectActiveTenantImageByTenantVersionIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewTenantImagesMethodImpl implements ViewTenantImagesMethod {

    final SelectActiveTenantImageByTenantVersionIdOperation selectActiveTenantImageByTenantVersionIdOperation;
    final SelectActiveTenantImageByTenantIdOperation selectActiveTenantImageByTenantIdOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantImagesResponse> execute(final ShardModel shardModel,
                                                 final ViewTenantImagesRequest request) {
        log.debug("{}", request);

        final var tenantId = request.getTenantId();
        return pgPool.withTransaction(sqlConnection -> {
                    final var tenantVersionId = request.getTenantVersionId();
                    if (Objects.nonNull(tenantVersionId)) {
                        return selectActiveTenantImageByTenantVersionIdOperation.execute(sqlConnection,
                                shardModel.slot(),
                                tenantId,
                                tenantVersionId);
                    } else {
                        return selectActiveTenantImageByTenantIdOperation.execute(sqlConnection,
                                shardModel.slot(),
                                tenantId);
                    }
                })
                .map(ViewTenantImagesResponse::new);
    }
}

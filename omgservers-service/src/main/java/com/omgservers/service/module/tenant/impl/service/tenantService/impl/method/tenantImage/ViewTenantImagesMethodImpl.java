package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImage;

import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantImage.SelectActiveTenantImageByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantImage.SelectActiveTenantImageByTenantVersionIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantImagesResponse> execute(final ViewTenantImagesRequest request) {
        log.debug("Requested, {}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    return pgPool.withTransaction(sqlConnection -> {
                                final var tenantVersionId = request.getTenantVersionId();
                                if (Objects.nonNull(tenantVersionId)) {
                                    return selectActiveTenantImageByTenantVersionIdOperation
                                            .execute(sqlConnection,
                                                    shard.shard(),
                                                    tenantId,
                                                    tenantVersionId);
                                } else {
                                    return selectActiveTenantImageByTenantIdOperation
                                            .execute(sqlConnection,
                                                    shard.shard(),
                                                    tenantId);
                                }
                            }
                    );
                })
                .map(ViewTenantImagesResponse::new);
    }
}

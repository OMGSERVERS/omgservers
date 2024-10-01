package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantImageRef;

import com.omgservers.schema.module.tenant.tenantImageRef.ViewTenantImageRefsRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.ViewTenantImageRefsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantImageRef.SelectActiveTenantImageRefsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantImageRef.SelectActiveTenantImageRefsByTenantVersionIdOperation;
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
class ViewTenantImageRefsMethodImpl implements ViewTenantImageRefsMethod {

    final SelectActiveTenantImageRefsByTenantVersionIdOperation selectActiveTenantImageRefsByTenantVersionIdOperation;
    final SelectActiveTenantImageRefsByTenantIdOperation selectActiveTenantImageRefsByTenantIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantImageRefsResponse> execute(final ViewTenantImageRefsRequest request) {
        log.debug("View tenant image refs, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    return pgPool.withTransaction(sqlConnection -> {
                                final var tenantVersionId = request.getTenantVersionId();
                                if (Objects.nonNull(tenantVersionId)) {
                                    return selectActiveTenantImageRefsByTenantVersionIdOperation
                                            .execute(sqlConnection,
                                                    shard.shard(),
                                                    tenantId,
                                                    tenantVersionId);
                                } else {
                                    return selectActiveTenantImageRefsByTenantIdOperation
                                            .execute(sqlConnection,
                                                    shard.shard(),
                                                    tenantId);
                                }
                            }
                    );
                })
                .map(ViewTenantImageRefsResponse::new);
    }
}

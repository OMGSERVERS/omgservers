package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantBuildRequest;

import com.omgservers.schema.module.tenant.tenantBuildRequest.ViewTenantBuildRequestsRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.ViewTenantBuildRequestsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantBuildRequest.SelectActiveTenantBuildRequestsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantBuildRequest.SelectActiveTenantBuildRequestsByTenantVersionIdOperation;
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
class ViewTenantBuildRequestsMethodImpl implements ViewTenantBuildRequestsMethod {

    final SelectActiveTenantBuildRequestsByTenantVersionIdOperation
            selectActiveTenantBuildRequestsByTenantVersionIdOperation;
    final SelectActiveTenantBuildRequestsByTenantIdOperation selectActiveTenantBuildRequestsByTenantIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantBuildRequestsResponse> execute(
            final ViewTenantBuildRequestsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    return pgPool.withTransaction(sqlConnection -> {
                        final var tenantVersionId = request.getTenantVersionId();
                        if (Objects.nonNull(tenantVersionId)) {
                            return selectActiveTenantBuildRequestsByTenantVersionIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            tenantVersionId);
                        } else {
                            return selectActiveTenantBuildRequestsByTenantIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId);
                        }
                    });
                })
                .map(ViewTenantBuildRequestsResponse::new);
    }
}

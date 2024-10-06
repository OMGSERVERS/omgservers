package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantJenkinsRequest;

import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.ViewTenantJenkinsRequestsResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantJenkinsRequest.SelectActiveTenantJenkinsRequestsByTenantIdOperation;
import com.omgservers.service.module.tenant.impl.operation.tenantJenkinsRequest.SelectActiveTenantJenkinsRequestsByTenantVersionIdOperation;
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
class ViewTenantJenkinsRequestsMethodImpl implements ViewTenantJenkinsRequestsMethod {

    final SelectActiveTenantJenkinsRequestsByTenantVersionIdOperation
            selectActiveTenantJenkinsRequestsByTenantVersionIdOperation;
    final SelectActiveTenantJenkinsRequestsByTenantIdOperation selectActiveTenantJenkinsRequestsByTenantIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewTenantJenkinsRequestsResponse> execute(
            final ViewTenantJenkinsRequestsRequest request) {
        log.debug("View tenant jenkins requests, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    return pgPool.withTransaction(sqlConnection -> {
                        final var tenantVersionId = request.getTenantVersionId();
                        if (Objects.nonNull(tenantVersionId)) {
                            return selectActiveTenantJenkinsRequestsByTenantVersionIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            tenantVersionId);
                        } else {
                            return selectActiveTenantJenkinsRequestsByTenantIdOperation
                                    .execute(sqlConnection,
                                            shard.shard(),
                                            tenantId);
                        }
                    });
                })
                .map(ViewTenantJenkinsRequestsResponse::new);
    }
}

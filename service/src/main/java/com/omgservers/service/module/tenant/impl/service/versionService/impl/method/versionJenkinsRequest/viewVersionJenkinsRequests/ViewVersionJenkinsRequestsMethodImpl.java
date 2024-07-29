package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.viewVersionJenkinsRequests;

import com.omgservers.schema.module.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.ViewVersionJenkinsRequestsResponse;
import com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.selectActiveVersionJenkinsRequestsByTenantId.SelectActiveVersionJenkinsRequestsByTenantId;
import com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.selectActiveVersionJenkinsRequestsByVersionId.SelectActiveVersionJenkinsRequestsByVersionId;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewVersionJenkinsRequestsMethodImpl implements ViewVersionJenkinsRequestsMethod {

    final SelectActiveVersionJenkinsRequestsByVersionId selectActiveVersionJenkinsRequestsByVersionId;
    final SelectActiveVersionJenkinsRequestsByTenantId selectActiveVersionJenkinsRequestsByTenantId;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewVersionJenkinsRequestsResponse> viewVersionJenkinsRequests(
            final ViewVersionJenkinsRequestsRequest request) {
        log.debug("View version jenkins requests, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    return pgPool.withTransaction(sqlConnection -> {
                        final var versionId = request.getVersionId();
                        if (Objects.nonNull(versionId)) {
                            return selectActiveVersionJenkinsRequestsByVersionId
                                    .selectActiveVersionJenkinsRequestsByVersionId(sqlConnection,
                                            shard.shard(),
                                            tenantId,
                                            versionId);
                        } else {
                            return selectActiveVersionJenkinsRequestsByTenantId
                                    .selectActiveVersionJenkinsRequestsByTenantId(sqlConnection,
                                            shard.shard(),
                                            tenantId);
                        }
                    });
                })
                .map(ViewVersionJenkinsRequestsResponse::new);
    }
}

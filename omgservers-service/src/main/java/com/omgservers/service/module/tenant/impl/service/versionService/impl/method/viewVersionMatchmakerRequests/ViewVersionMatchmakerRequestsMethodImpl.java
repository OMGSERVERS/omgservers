package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionMatchmakerRequests;

import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRequestsRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRequestsResponse;
import com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakerRequestsByVersionId.SelectActiveVersionMatchmakerRequestsByVersionId;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewVersionMatchmakerRequestsMethodImpl implements ViewVersionMatchmakerRequestsMethod {

    final SelectActiveVersionMatchmakerRequestsByVersionId selectActiveVersionMatchmakerRequestsByVersionId;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewVersionMatchmakerRequestsResponse> viewVersionMatchmakerRequests(
            final ViewVersionMatchmakerRequestsRequest request) {
        log.debug("View version matchmaker requests, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveVersionMatchmakerRequestsByVersionId
                            .selectActiveVersionMatchmakerRequestsByVersionId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    versionId));
                })
                .map(ViewVersionMatchmakerRequestsResponse::new);

    }
}

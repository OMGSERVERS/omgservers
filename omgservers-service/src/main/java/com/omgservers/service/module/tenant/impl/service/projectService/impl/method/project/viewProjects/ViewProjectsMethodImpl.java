package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.project.viewProjects;

import com.omgservers.model.dto.tenant.ViewProjectsRequest;
import com.omgservers.model.dto.tenant.ViewProjectsResponse;
import com.omgservers.service.module.tenant.impl.operation.project.selectActiveProjectsByTenantId.SelectActiveProjectsByTenantIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewProjectsMethodImpl implements ViewProjectsMethod {

    final SelectActiveProjectsByTenantIdOperation selectActiveProjectsByTenantIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewProjectsResponse> viewProjects(ViewProjectsRequest request) {
        log.debug("View projects, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveProjectsByTenantIdOperation
                            .selectActiveProjectsByTenantId(sqlConnection,
                                    shard.shard(),
                                    tenantId
                            ));
                })
                .map(ViewProjectsResponse::new);

    }
}

package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.viewProjects;

import com.omgservers.model.dto.tenant.ViewProjectsRequest;
import com.omgservers.model.dto.tenant.ViewProjectsResponse;
import com.omgservers.service.module.tenant.impl.operation.selectProjectsByTenantId.SelectProjectsByTenantIdOperation;
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

    final SelectProjectsByTenantIdOperation selectProjectsByTenantIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewProjectsResponse> viewProjects(ViewProjectsRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var deleted = request.getDeleted();
                    return pgPool.withTransaction(sqlConnection -> selectProjectsByTenantIdOperation
                            .selectProjectsByTenantId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    deleted));
                })
                .map(ViewProjectsResponse::new);

    }
}

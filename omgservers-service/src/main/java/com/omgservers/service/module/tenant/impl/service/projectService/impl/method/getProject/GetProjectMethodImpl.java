package com.omgservers.service.module.tenant.impl.service.projectService.impl.method.getProject;

import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.service.module.tenant.impl.operation.selectProject.SelectProjectOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class
GetProjectMethodImpl implements GetProjectMethod {

    final SelectProjectOperation selectProjectOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetProjectResponse> getProject(final GetProjectRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    final var deleted = request.getDeleted();
                    return pgPool.withTransaction(sqlConnection -> selectProjectOperation
                            .selectProject(sqlConnection, shard.shard(), tenantId, id, deleted));
                })
                .map(GetProjectResponse::new);
    }
}

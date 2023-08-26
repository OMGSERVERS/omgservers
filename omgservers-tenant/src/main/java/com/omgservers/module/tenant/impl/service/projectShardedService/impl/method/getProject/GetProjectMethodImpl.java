package com.omgservers.module.tenant.impl.service.projectShardedService.impl.method.getProject;

import com.omgservers.dto.tenantModule.GetProjectShardRequest;
import com.omgservers.module.tenant.impl.operation.selectProject.SelectProjectOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
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
    public Uni<GetProjectInternalResponse> getProject(final GetProjectShardRequest request) {
        GetProjectShardRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectProjectOperation
                            .selectProject(sqlConnection, shard.shard(), id));
                })
                .map(GetProjectInternalResponse::new);
    }
}

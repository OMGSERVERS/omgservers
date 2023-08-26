package com.omgservers.application.module.tenantModule.impl.service.projectInternalService.impl.method.getProjectMethod;

import com.omgservers.application.module.tenantModule.impl.operation.selectProjectOperation.SelectProjectOperation;
import com.omgservers.base.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.tenantModule.GetProjectRoutedRequest;
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
    public Uni<GetProjectInternalResponse> getProject(final GetProjectRoutedRequest request) {
        GetProjectRoutedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectProjectOperation
                            .selectProject(sqlConnection, shard.shard(), id));
                })
                .map(GetProjectInternalResponse::new);
    }
}

package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.viewStagePermissions;

import com.omgservers.model.dto.tenant.ViewStagePermissionsRequest;
import com.omgservers.model.dto.tenant.ViewStagePermissionsResponse;
import com.omgservers.service.module.tenant.impl.operation.stagePermission.selectActiveStagePermissionsByStageId.SelectActiveStagePermissionsByStageIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewStagePermissionsMethodImpl implements ViewStagePermissionsMethod {

    final SelectActiveStagePermissionsByStageIdOperation selectActiveStagePermissionsByStageIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewStagePermissionsResponse> viewStagePermissions(final ViewStagePermissionsRequest request) {
        log.debug("View stage permissions, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var stageId = request.getStageId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveStagePermissionsByStageIdOperation
                            .selectActiveStagePermissionsByStageId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    stageId
                            ));
                })
                .map(ViewStagePermissionsResponse::new);

    }
}

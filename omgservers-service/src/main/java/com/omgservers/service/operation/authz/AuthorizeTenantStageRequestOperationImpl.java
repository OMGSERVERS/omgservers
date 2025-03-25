package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantStagePermissionOperation;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.alias.GetIdByStageOperation;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.shard.deployment.DeploymentShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorizeTenantStageRequestOperationImpl implements AuthorizeTenantStageRequestOperation {

    final DeploymentShard deploymentShard;

    final CheckTenantStagePermissionOperation checkTenantStagePermissionOperation;

    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByStageOperation getIdByStageOperation;

    @Override
    public Uni<TenantStageAuthorization> execute(final String tenant,
                                                final String project,
                                                final String stage,
                                                final Long userId,
                                                final TenantStagePermissionQualifierEnum permission) {
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> getIdByProjectOperation.execute(tenantId, project)
                        .flatMap(tenantProjectId -> getIdByStageOperation.execute(tenantId, tenantProjectId, stage)
                                .flatMap(tenantStageId -> checkTenantStagePermissionOperation.execute(tenantId,
                                                tenantStageId,
                                                userId,
                                                permission)
                                        .map(voidItem -> new TenantStageAuthorization(tenantId,
                                                tenantProjectId,
                                                tenantStageId,
                                                tenantStageId,
                                                permission))))
                );
    }
}

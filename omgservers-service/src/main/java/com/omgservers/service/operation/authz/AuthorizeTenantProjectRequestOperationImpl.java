package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
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
class AuthorizeTenantProjectRequestOperationImpl implements AuthorizeTenantProjectRequestOperation {

    final DeploymentShard deploymentShard;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;

    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByStageOperation getIdByStageOperation;

    @Override
    public Uni<TenantProjectAuthorization> execute(final String tenant,
                                                   final String project,
                                                   final Long userId,
                                                   final TenantProjectPermissionQualifierEnum permission) {
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> getIdByProjectOperation.execute(tenantId, project)
                        .flatMap(tenantProjectId -> checkTenantProjectPermissionOperation.execute(tenantId,
                                        tenantProjectId,
                                        userId,
                                        permission)
                                .map(voidItem -> new TenantProjectAuthorization(tenantId,
                                        tenantProjectId,
                                        userId,
                                        permission)))
                );
    }
}

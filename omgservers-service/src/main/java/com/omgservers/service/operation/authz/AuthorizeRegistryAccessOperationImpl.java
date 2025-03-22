package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.operation.alias.GetIdByVersionOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorizeRegistryAccessOperationImpl implements AuthorizeRegistryAccessOperation {

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final GetIdByVersionOperation getIdByVersionOperation;
    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    @Override
    public Uni<RegistryAccessAuthorization> execute(final String tenant,
                                                    final String project,
                                                    final Long userId,
                                                    final TenantProjectPermissionQualifierEnum permission) {
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> getIdByProjectOperation.execute(tenantId, project)
                        .flatMap(tenantProjectId -> checkTenantProjectPermissionOperation.execute(tenantId,
                                        tenantProjectId,
                                        userId,
                                        permission)
                                .map(voidItem -> new RegistryAccessAuthorization(tenantId,
                                        tenantProjectId,
                                        userId,
                                        permission)
                                )
                        )
                );
    }
}

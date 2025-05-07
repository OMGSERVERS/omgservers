package com.omgservers.service.operation.authz;

import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantPermissionOperation;
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
class AuthorizeTenantRequestOperationImpl implements AuthorizeTenantRequestOperation {

    final CheckTenantPermissionOperation checkTenantPermissionOperation;
    final GetIdByVersionOperation getIdByVersionOperation;
    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    @Override
    public Uni<TenantAuthorization> execute(final String tenant,
                                            final Long userId,
                                            final TenantPermissionQualifierEnum permission) {
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> checkTenantPermissionOperation.execute(tenantId,
                                userId,
                                permission)
                        .map(voidItem -> new TenantAuthorization(tenantId,
                                userId,
                                permission)
                        )
                );
    }
}

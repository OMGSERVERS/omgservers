package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateTenantProjectAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectAliasDeveloperResponse;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantPermissionOperation;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.module.alias.AliasModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getIdByTenant.GetIdByTenantOperation;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTenantProjectAliasMethodImpl implements CreateTenantProjectAliasMethod {

    final TenantModule tenantModule;
    final AliasModule aliasModule;
    final UserModule userModule;

    final CheckTenantPermissionOperation checkTenantPermissionOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final AliasModelFactory aliasModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantProjectAliasDeveloperResponse> execute(
            final CreateTenantProjectAliasDeveloperRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var permissionQualifier =
                            TenantPermissionQualifierEnum.PROJECT_MANAGER;
                    return checkTenantPermissionOperation.execute(tenantId, userId, permissionQualifier)
                            .flatMap(voidItem -> {
                                final var tenantProjectId = request.getProjectId();
                                final var aliasValue = request.getAlias();
                                return createTenantProjectAlias(tenantId, tenantProjectId, aliasValue, userId);
                            });
                })
                .replaceWith(new CreateTenantProjectAliasDeveloperResponse());


    }

    Uni<AliasModel> createTenantProjectAlias(final Long tenantId,
                                             final Long tenantProjectId,
                                             final String aliasValue,
                                             final Long userId) {
        final var tenantProjectAlias = aliasModelFactory.create(AliasQualifierEnum.PROJECT,
                tenantId,
                tenantId,
                tenantProjectId,
                aliasValue);
        final var syncAliasRequest = new SyncAliasRequest(tenantProjectAlias);
        return aliasModule.getService().execute(syncAliasRequest)
                .invoke(response -> {
                    if (response.getCreated()) {
                        log.info("The alias \"{}\" for the project \"{}\" was created by the user \"{}\"",
                                aliasValue, tenantProjectId, userId);
                    }
                })
                .replaceWith(tenantProjectAlias);
    }
}

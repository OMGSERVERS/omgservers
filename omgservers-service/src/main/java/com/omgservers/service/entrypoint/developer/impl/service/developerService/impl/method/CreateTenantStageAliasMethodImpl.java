package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateTenantStageAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageAliasDeveloperResponse;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
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
class CreateTenantStageAliasMethodImpl implements CreateTenantStageAliasMethod {

    final TenantModule tenantModule;
    final AliasModule aliasModule;
    final UserModule userModule;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final AliasModelFactory aliasModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantStageAliasDeveloperResponse> execute(
            final CreateTenantStageAliasDeveloperRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var tenantStageId = request.getStageId();
                    return getTenantStage(tenantId, tenantStageId)
                            .flatMap(tenantStage -> {
                                final var tenantProjectId = tenantStage.getProjectId();
                                final var permissionQualifier =
                                        TenantProjectPermissionQualifierEnum.STAGE_MANAGER;
                                return checkTenantProjectPermissionOperation.execute(tenantId,
                                                tenantProjectId,
                                                userId,
                                                permissionQualifier)
                                        .flatMap(voidItem -> {
                                            final var aliasValue = request.getAlias();
                                            return createTenantStageAlias(tenantId,
                                                    tenantProjectId,
                                                    tenantStageId,
                                                    aliasValue,
                                                    userId);
                                        });
                            });
                })
                .replaceWith(new CreateTenantStageAliasDeveloperResponse());
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long tenantStageId) {
        final var request = new GetTenantStageRequest(tenantId, tenantStageId);
        return tenantModule.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<AliasModel> createTenantStageAlias(final Long tenantId,
                                           final Long tenantProjectId,
                                           final Long tenantStageId,
                                           final String aliasValue,
                                           final Long userId) {
        final var tenantStageAlias = aliasModelFactory.create(AliasQualifierEnum.STAGE,
                tenantId,
                tenantProjectId,
                tenantStageId,
                aliasValue);
        final var syncAliasRequest = new SyncAliasRequest(tenantStageAlias);
        return aliasModule.getService().execute(syncAliasRequest)
                .invoke(response -> {
                    if (response.getCreated()) {
                        log.info("The alias \"{}\" for the stage \"{}\" was created by the user \"{}\"",
                                aliasValue, tenantStageId, userId);
                    }
                })
                .replaceWith(tenantStageAlias);
    }
}

package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportResponse;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.module.alias.AliasModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTenantProjectAliasMethodImpl implements CreateTenantProjectAliasMethod {

    final TenantModule tenantModule;
    final AliasModule aliasModule;

    final AliasModelFactory aliasModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantProjectAliasSupportResponse> execute(final CreateTenantProjectAliasSupportRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenant(tenant)
                .flatMap(tenantId -> {
                    final var tenantProjectId = request.getProjectId();
                    return getTenantProject(tenantId, tenantProjectId)
                            .flatMap(tenantProject -> {
                                final var aliasValue = request.getAlias();
                                return createProjectAlias(tenantId, tenantProjectId, aliasValue, userId);
                            });
                })
                .replaceWith(new CreateTenantProjectAliasSupportResponse());
    }

    Uni<Long> getIdByTenant(final String tenant) {
        try {
            final var tenantId = Long.valueOf(tenant);
            return Uni.createFrom().item(tenantId);
        } catch (NumberFormatException e) {
            return findTenantAlias(tenant)
                    .map(AliasModel::getEntityId);
        }
    }

    Uni<AliasModel> findTenantAlias(final String tenantAlias) {
        final var request = new FindAliasRequest(DefaultAliasConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_TENANTS_GROUP,
                tenantAlias);
        return aliasModule.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<TenantProjectModel> getTenantProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantModule.getService().getTenantProject(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }

    Uni<AliasModel> createProjectAlias(final Long tenantId,
                                       final Long tenantProjectId,
                                       final String aliasValue,
                                       final Long userId) {
        final var projectAlias = aliasModelFactory.create(AliasQualifierEnum.PROJECT,
                tenantId,
                tenantId,
                tenantProjectId,
                aliasValue);
        final var syncAliasRequest = new SyncAliasRequest(projectAlias);
        return aliasModule.getService().execute(syncAliasRequest)
                .invoke(response -> {
                    if (response.getCreated()) {
                        log.info("The alias \"{}\" for the project \"{}\" was created by the user \"{}\"",
                                aliasValue, tenantProjectId, userId);
                    }
                })
                .replaceWith(projectAlias);
    }
}

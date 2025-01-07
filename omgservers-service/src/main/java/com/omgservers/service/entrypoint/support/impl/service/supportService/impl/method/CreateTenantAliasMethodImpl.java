package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportResponse;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.module.alias.AliasModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.security.SecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTenantAliasMethodImpl implements CreateTenantAliasMethod {

    final TenantModule tenantModule;
    final AliasModule aliasModule;

    final AliasModelFactory aliasModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantAliasSupportResponse> execute(final CreateTenantAliasSupportRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        return getTenant(tenantId)
                .flatMap(tenant -> {
                    final var aliasValue = request.getAlias();
                    return createTenantAlias(tenantId, aliasValue, userId);
                })
                .replaceWith(new CreateTenantAliasSupportResponse());
    }

    Uni<TenantModel> getTenant(final Long tenantId) {
        final var getTenantRequest = new GetTenantRequest(tenantId);
        return tenantModule.getService().getTenant(getTenantRequest)
                .map(GetTenantResponse::getTenant);
    }

    Uni<AliasModel> createTenantAlias(final Long tenantId,
                                      final String aliasValue,
                                      final Long userId) {
        final var alias = aliasModelFactory.create(AliasQualifierEnum.TENANT,
                DefaultAliasConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_TENANTS_GROUP,
                tenantId,
                aliasValue);
        final var syncAliasRequest = new SyncAliasRequest(alias);
        return aliasModule.getService().execute(syncAliasRequest)
                .invoke(response -> {
                    if (response.getCreated()) {
                        log.info("The alias \"{}\" for the tenant \"{}\" was created by the user \"{}\"",
                                aliasValue, tenantId, userId);
                    }
                })
                .replaceWith(alias);
    }
}

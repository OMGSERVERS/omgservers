package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportResponse;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.operation.alias.CreateTenantAliasOperation;
import com.omgservers.service.operation.alias.CreateTenantAliasResult;
import com.omgservers.service.security.SecurityAttributesEnum;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateTenantAliasMethodImpl implements CreateTenantAliasMethod {

    final TenantShard tenantShard;
    final AliasShard aliasShard;

    final CreateTenantAliasOperation createTenantAliasOperation;

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
                    return createTenantAliasOperation.execute(tenantId, aliasValue);
                })
                .map(CreateTenantAliasResult::created)
                .map(CreateTenantAliasSupportResponse::new);
    }

    Uni<TenantModel> getTenant(final Long tenantId) {
        final var getTenantRequest = new GetTenantRequest(tenantId);
        return tenantShard.getService().execute(getTenantRequest)
                .map(GetTenantResponse::getTenant);
    }
}

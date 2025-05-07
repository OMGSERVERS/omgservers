package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportResponse;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.shard.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.operation.alias.CreateTenantProjectAliasOperation;
import com.omgservers.service.operation.alias.CreateTenantProjectAliasResult;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
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
class CreateTenantProjectAliasMethodImpl implements CreateTenantProjectAliasMethod {

    final TenantShard tenantShard;
    final AliasShard aliasShard;

    final CreateTenantProjectAliasOperation createTenantProjectAliasOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final AliasModelFactory aliasModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantProjectAliasSupportResponse> execute(final CreateTenantProjectAliasSupportRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var tenantProjectId = request.getProjectId();
                    return getTenantProject(tenantId, tenantProjectId)
                            .flatMap(tenantProject -> {
                                final var aliasValue = request.getAlias();
                                return createTenantProjectAliasOperation.execute(tenantId,
                                        tenantProjectId,
                                        aliasValue);
                            });
                })
                .map(CreateTenantProjectAliasResult::created)
                .map(CreateTenantProjectAliasSupportResponse::new);
    }

    Uni<TenantProjectModel> getTenantProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }
}

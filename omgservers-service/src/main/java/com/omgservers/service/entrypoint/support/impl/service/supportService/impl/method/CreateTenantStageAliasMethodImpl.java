package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportResponse;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.operation.alias.CreateTenantStageAliasOperation;
import com.omgservers.service.operation.alias.CreateTenantStageAliasResult;
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
class CreateTenantStageAliasMethodImpl implements CreateTenantStageAliasMethod {

    final TenantShard tenantShard;
    final AliasShard aliasShard;

    final CreateTenantStageAliasOperation createTenantStageAliasOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final AliasModelFactory aliasModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantStageAliasSupportResponse> execute(final CreateTenantStageAliasSupportRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var tenantStageId = request.getStageId();
                    return getTenantStage(tenantId, tenantStageId)
                            .flatMap(tenantStage -> {
                                final var tenantProjectId = tenantStage.getProjectId();
                                final var aliasValue = request.getAlias();
                                return createTenantStageAliasOperation.execute(tenantId,
                                        tenantProjectId,
                                        tenantStageId,
                                        aliasValue);
                            });
                })
                .map(CreateTenantStageAliasResult::created)
                .map(CreateTenantStageAliasSupportResponse::new);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }
}

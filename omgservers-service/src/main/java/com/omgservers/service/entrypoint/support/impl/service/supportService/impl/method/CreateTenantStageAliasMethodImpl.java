package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method;

import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportResponse;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
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
class CreateTenantStageAliasMethodImpl implements CreateTenantStageAliasMethod {

    final TenantShard tenantShard;
    final AliasShard aliasShard;

    final GetIdByTenantOperation getIdByTenantOperation;

    final AliasModelFactory aliasModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantStageAliasSupportResponse> execute(final CreateTenantStageAliasSupportRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var tenantStageId = request.getStageId();
                    return getTenantStage(tenantId, tenantStageId)
                            .flatMap(tenantStage -> {
                                final var tenantProjectId = tenantStage.getProjectId();
                                final var aliasValue = request.getAlias();
                                return createTenantStageAlias(tenantId, tenantProjectId, tenantStageId, aliasValue);
                            });
                })
                .replaceWith(new CreateTenantStageAliasSupportResponse());
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<AliasModel> createTenantStageAlias(final Long tenantId,
                                           final Long tenantProjectId,
                                           final Long tenantStageId,
                                           final String aliasValue) {
        final var tenantStageAlias = aliasModelFactory.create(AliasQualifierEnum.STAGE,
                tenantId,
                tenantProjectId,
                tenantStageId,
                aliasValue);
        final var syncAliasRequest = new SyncAliasRequest(tenantStageAlias);
        return aliasShard.getService().execute(syncAliasRequest)
                .invoke(response -> {
                    if (response.getCreated()) {
                        log.info("The alias \"{}\" for the stage \"{}\" was created",
                                aliasValue, tenantStageId);
                    }
                })
                .replaceWith(tenantStageAlias);
    }
}

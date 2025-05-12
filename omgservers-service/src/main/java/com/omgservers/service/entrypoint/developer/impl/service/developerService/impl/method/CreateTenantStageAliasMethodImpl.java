package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateStageAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateStageAliasDeveloperResponse;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.shard.alias.SyncAliasRequest;
import com.omgservers.schema.shard.alias.SyncAliasResponse;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.operation.alias.CreateTenantStageAliasOperation;
import com.omgservers.service.operation.alias.CreateTenantStageAliasResult;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.operation.authz.AuthorizeTenantProjectRequestOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.tenant.TenantShard;
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

    final TenantShard tenantShard;
    final AliasShard aliasShard;

    final AuthorizeTenantProjectRequestOperation authorizeTenantProjectRequestOperation;
    final CreateTenantStageAliasOperation createTenantStageAliasOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final AliasModelFactory aliasModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateStageAliasDeveloperResponse> execute(final CreateStageAliasDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var userId = securityIdentity.<Long>getAttribute(
                SecurityAttributesEnum.USER_ID.getAttributeName());

        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var tenantStageId = request.getStageId();
                    return getTenantStage(tenantId, tenantStageId)
                            .flatMap(tenantStage -> {
                                final var tenantProjectId = tenantStage.getProjectId();
                                final var permission = TenantProjectPermissionQualifierEnum.STAGE_MANAGER;
                                return authorizeTenantProjectRequestOperation.execute(tenantId.toString(),
                                                tenantProjectId.toString(),
                                                userId,
                                                permission)
                                        .flatMap(authorization -> {
                                            final var aliasValue = request.getAlias();
                                            return createTenantStageAliasOperation.execute(tenantId,
                                                    tenantProjectId,
                                                    tenantStageId,
                                                    aliasValue);
                                        });
                            });
                })
                .map(CreateTenantStageAliasResult::created)
                .map(CreateStageAliasDeveloperResponse::new);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long tenantStageId) {
        final var request = new GetTenantStageRequest(tenantId, tenantStageId);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }
}

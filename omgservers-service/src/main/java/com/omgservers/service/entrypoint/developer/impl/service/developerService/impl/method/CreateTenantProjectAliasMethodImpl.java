package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateProjectAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectAliasDeveloperResponse;
import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantPermissionOperation;
import com.omgservers.service.factory.alias.AliasModelFactory;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.user.UserShard;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
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

    final TenantShard tenantShard;
    final AliasShard aliasShard;
    final UserShard userShard;

    final CheckTenantPermissionOperation checkTenantPermissionOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final AliasModelFactory aliasModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateProjectAliasDeveloperResponse> execute(
            final CreateProjectAliasDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

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
                .replaceWith(new CreateProjectAliasDeveloperResponse());


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
        return aliasShard.getService().execute(syncAliasRequest)
                .invoke(response -> {
                    if (response.getCreated()) {
                        log.info("The alias \"{}\" for the project \"{}\" was created",
                                aliasValue, tenantProjectId);
                    }
                })
                .replaceWith(tenantProjectAlias);
    }
}

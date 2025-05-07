package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperResponse;
import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenant.GetTenantDataRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantDataResponse;
import com.omgservers.schema.shard.tenant.tenant.dto.TenantDataDto;
import com.omgservers.service.entrypoint.developer.impl.mappers.TenantMapper;
import com.omgservers.service.operation.authz.AuthorizeTenantRequestOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
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
class GetTenantDetailsMethodImpl implements GetTenantDetailsMethod {

    final TenantShard tenantShard;

    final AuthorizeTenantRequestOperation authorizeTenantRequestOperation;

    final SecurityIdentity securityIdentity;
    final TenantMapper tenantMapper;


    @Override
    public Uni<GetTenantDetailsDeveloperResponse> execute(final GetTenantDetailsDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var userId = securityIdentity.<Long>getAttribute(
                SecurityAttributesEnum.USER_ID.getAttributeName());
        final var permission = TenantPermissionQualifierEnum.TENANT_VIEWER;

        return authorizeTenantRequestOperation.execute(tenant, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    return getTenantData(tenantId)
                            .map(tenantMapper::dataToDetails);
                })
                .map(GetTenantDetailsDeveloperResponse::new);
    }

    Uni<TenantDataDto> getTenantData(final Long tenantId) {
        final var request = new GetTenantDataRequest(tenantId);
        return tenantShard.getService().execute(request)
                .map(GetTenantDataResponse::getTenantData);
    }
}

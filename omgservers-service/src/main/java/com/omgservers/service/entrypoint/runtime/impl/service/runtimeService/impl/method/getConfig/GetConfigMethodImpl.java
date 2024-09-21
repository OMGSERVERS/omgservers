package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.getConfig;

import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeResponse;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.security.ServiceSecurityAttributes;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetConfigMethodImpl implements GetConfigMethod {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetConfigRuntimeResponse> getConfig(final GetConfigRuntimeRequest request) {
        log.debug("Get config, request={}", request);

        final var runtimeId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributes.RUNTIME_ID.getAttributeName());

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    final var tenantId = runtime.getTenantId();
                    final var versionId = runtime.getDeploymentId();
                    return getVersion(tenantId, versionId)
                            .map(TenantVersionModel::getConfig);
                })
                .map(GetConfigRuntimeResponse::new);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }
}

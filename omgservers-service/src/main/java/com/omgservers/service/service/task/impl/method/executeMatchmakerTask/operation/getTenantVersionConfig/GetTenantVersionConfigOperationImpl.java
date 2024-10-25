package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.getTenantVersionConfig;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionConfigResponse;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class GetTenantVersionConfigOperationImpl implements GetTenantVersionConfigOperation {

    final TenantModule tenantModule;

    @Override
    public Uni<TenantVersionConfigDto> execute(final MatchmakerModel matchmaker) {
        final var tenantId = matchmaker.getTenantId();
        final var tenantDeploymentId = matchmaker.getDeploymentId();
        return getTenantDeployment(tenantId, tenantDeploymentId)
                .flatMap(tenantDeployment -> {
                    final var tenantVersionId = tenantDeployment.getVersionId();
                    return getTenantVersionConfig(tenantId, tenantVersionId);
                });
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<TenantVersionConfigDto> getTenantVersionConfig(final Long tenantId, final Long tenantVersionId) {
        final var request = new GetTenantVersionConfigRequest(tenantId, tenantVersionId);
        return tenantModule.getService().getTenantVersionConfig(request)
                .map(GetTenantVersionConfigResponse::getTenantVersionConfig);
    }
}

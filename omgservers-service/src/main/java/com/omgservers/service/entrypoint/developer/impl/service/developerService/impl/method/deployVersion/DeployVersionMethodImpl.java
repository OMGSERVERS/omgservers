package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.tenant.TenantDeploymentModelFactory;
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
class DeployVersionMethodImpl implements DeployVersionMethod {

    final TenantModule tenantModule;

    final TenantDeploymentModelFactory tenantDeploymentModelFactory;

    final SecurityIdentity securityIdentity;
    final ObjectMapper objectMapper;

    @Override
    public Uni<DeployVersionDeveloperResponse> deployVersion(final DeployVersionDeveloperRequest request) {
        log.debug("Deploy version, request={}", request);

        final var tenantId = request.getTenantId();
        final var tenantVersionId = request.getTenantVersionId();
        final var tenantStageId = request.getTenantStageId();

        return getTenantVersion(tenantId, tenantVersionId)
                .flatMap(tenantVersion -> getTenantStage(tenantId, tenantStageId)
                        .flatMap(tenantStage -> {
                            final var versionProjectId = tenantVersion.getProjectId();
                            final var stageProjectId = tenantStage.getProjectId();
                            if (versionProjectId.equals(stageProjectId)) {
                                final var userId = securityIdentity.<Long>getAttribute(
                                        ServiceSecurityAttributes.USER_ID.getAttributeName());
                                return checkVersionManagementPermission(tenantId, stageProjectId, userId)
                                        .flatMap(voidItem -> createTenantDeployment(tenantId,
                                                tenantVersionId,
                                                tenantStageId))
                                        .replaceWith(new DeployVersionDeveloperResponse());
                            } else {
                                throw new ServerSideForbiddenException(ExceptionQualifierEnum.WRONG_REQUEST);
                            }
                        })
                );
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long versionId) {
        final var request = new GetTenantVersionRequest(tenantId, versionId);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long stageId) {
        final var request = new GetTenantStageRequest(tenantId, stageId);
        return tenantModule.getTenantService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Void> checkVersionManagementPermission(final Long tenantId,
                                               final Long tenantProjectId,
                                               final Long userId) {
        final var permission = TenantProjectPermissionEnum.VERSION_MANAGEMENT;
        final var request = new VerifyTenantProjectPermissionExistsRequest(tenantId,
                tenantProjectId,
                userId,
                permission);
        return tenantModule.getTenantService().verifyTenantProjectPermissionExists(request)
                .map(VerifyTenantProjectPermissionExistsResponse::getExists)
                .invoke(exists -> {
                    if (!exists) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, " +
                                                "tenantId=%d, tenantProjectId=%d, userId=%d, permission=%s",
                                        tenantId, tenantProjectId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<TenantDeploymentModel> createTenantDeployment(final Long tenantId,
                                                      final Long tenantVersionId,
                                                      final Long tenantStageId) {
        final var tenantDeployment = tenantDeploymentModelFactory.create(tenantId,
                tenantStageId,
                tenantVersionId);

        final var request = new SyncTenantDeploymentRequest(tenantDeployment);
        return tenantModule.getTenantService().syncTenantDeployment(request)
                .replaceWith(tenantDeployment);
    }
}

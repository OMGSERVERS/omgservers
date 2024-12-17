package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantDeployment.SyncTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantStagePermissionOperation;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.tenant.TenantDeploymentModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeployTenantVersionMethodImpl implements DeployTenantVersionMethod {

    final TenantModule tenantModule;

    final CheckTenantStagePermissionOperation checkTenantStagePermissionOperation;

    final TenantDeploymentModelFactory tenantDeploymentModelFactory;
    final SecurityIdentity securityIdentity;
    final ObjectMapper objectMapper;

    @Override
    public Uni<DeployTenantVersionDeveloperResponse> execute(final DeployTenantVersionDeveloperRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getStageId();
        final var tenantVersionId = request.getVersionId();

        return verifyAtLeastOneTenantImageExists(tenantId, tenantVersionId)
                .flatMap(voidItem -> getTenantVersion(tenantId, tenantVersionId))
                .flatMap(tenantVersion -> getTenantStage(tenantId, tenantStageId)
                        .flatMap(tenantStage -> {
                            final var versionProjectId = tenantVersion.getProjectId();
                            final var stageProjectId = tenantStage.getProjectId();
                            if (versionProjectId.equals(stageProjectId)) {
                                final var userId = securityIdentity.<Long>getAttribute(
                                        ServiceSecurityAttributesEnum.USER_ID.getAttributeName());
                                final var permissionQualifier =
                                        TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGEMENT;
                                return checkTenantStagePermissionOperation.execute(tenantId,
                                                tenantStageId,
                                                userId,
                                                permissionQualifier)
                                        .flatMap(voidItem -> createTenantDeployment(tenantId,
                                                tenantVersionId,
                                                tenantStageId))
                                        .map(tenantDeployment -> {
                                            final var tenantDeploymentId = tenantDeployment.getId();

                                            log.info("New deployment {} was created in tenant {} by user {}",
                                                    tenantDeploymentId, tenantId, userId);

                                            return new DeployTenantVersionDeveloperResponse(tenantDeploymentId);
                                        });
                            } else {
                                throw new ServerSideForbiddenException(ExceptionQualifierEnum.WRONG_REQUEST);
                            }
                        })
                );
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long tenantVersionId) {
        final var request = new GetTenantVersionRequest(tenantId, tenantVersionId);
        return tenantModule.getService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long tenantStageId) {
        final var request = new GetTenantStageRequest(tenantId, tenantStageId);
        return tenantModule.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Void> verifyAtLeastOneTenantImageExists(final Long tenantId, final Long tenantVersionId) {
        final var request = new ViewTenantImagesRequest(tenantId, tenantVersionId);
        return tenantModule.getService().viewTenantImages(request)
                .map(ViewTenantImagesResponse::getTenantImages)
                .invoke(tenantImages -> {
                    if (tenantImages.isEmpty()) {
                        throw new ServerSideConflictException(ExceptionQualifierEnum.WRONG_VERSION_STATE);
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
        return tenantModule.getService().syncTenantDeployment(request)
                .replaceWith(tenantDeployment);
    }
}

package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.SyncTenantDeploymentResourceRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantDeploymentResourceModelFactory;
import com.omgservers.service.operation.authz.AuthorizeTenantStageRequestOperation;
import com.omgservers.service.operation.authz.AuthorizeTenantVersionRequestOperation;
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
class CreateDeploymentMethodImpl implements CreateDeploymentMethod {

    final TenantShard tenantShard;

    final AuthorizeTenantVersionRequestOperation authorizeTenantVersionRequestOperation;
    final AuthorizeTenantStageRequestOperation authorizeTenantStageRequestOperation;

    final TenantDeploymentResourceModelFactory tenantDeploymentResourceModelFactory;
    final SecurityIdentity securityIdentity;
    final ObjectMapper objectMapper;

    @Override
    public Uni<CreateDeploymentDeveloperResponse> execute(final CreateDeploymentDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var project = request.getProject();
        final var stage = request.getStage();
        final var userId = securityIdentity.<Long>getAttribute(
                SecurityAttributesEnum.USER_ID.getAttributeName());
        final var permission = TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER;

        return authorizeTenantStageRequestOperation.execute(tenant, project, stage, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    final var tenantStageId = authorization.tenantStageId();
                    final var tenantVersionId = request.getVersionId();
                    return createDeployment(tenantId, tenantStageId, tenantVersionId);
                })
                .map(CreateDeploymentDeveloperResponse::new);
    }

    Uni<Long> createDeployment(final Long tenantId,
                               final Long tenantStageId,
                               final Long tenantVersionId) {
        return verifyAtLeastOneTenantImageExists(tenantId, tenantVersionId)
                .flatMap(voidItem -> getTenantVersion(tenantId, tenantVersionId))
                .flatMap(tenantVersion -> getTenantStage(tenantId, tenantStageId)
                        .flatMap(tenantStage -> {
                            final var versionProjectId = tenantVersion.getProjectId();
                            final var stageProjectId = tenantStage.getProjectId();
                            // Version and stage must belong to the same project
                            if (versionProjectId.equals(stageProjectId)) {
                                return createTenantDeploymentResource(tenantId, tenantStageId, tenantVersionId)
                                        .map(tenantDeploymentResourceModel -> {
                                            final var deploymentId = tenantDeploymentResourceModel.getDeploymentId();
                                            log.info("Created new deployment \"{}\" for version \"{}\"",
                                                    deploymentId, tenantVersionId);
                                            return deploymentId;
                                        });
                            } else {
                                throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_REQUEST);
                            }
                        })
                );
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long tenantVersionId) {
        final var request = new GetTenantVersionRequest(tenantId, tenantVersionId);
        return tenantShard.getService().execute(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long tenantStageId) {
        final var request = new GetTenantStageRequest(tenantId, tenantStageId);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Void> verifyAtLeastOneTenantImageExists(final Long tenantId, final Long tenantVersionId) {
        final var request = new ViewTenantImagesRequest(tenantId, tenantVersionId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantImagesResponse::getTenantImages)
                .invoke(tenantImages -> {
                    if (tenantImages.isEmpty()) {
                        throw new ServerSideConflictException(ExceptionQualifierEnum.WRONG_VERSION_STATE);
                    }
                })
                .replaceWithVoid();
    }

    Uni<TenantDeploymentResourceModel> createTenantDeploymentResource(final Long tenantId,
                                                                      final Long tenantStageId,
                                                                      final Long tenantVersionId) {
        final var tenantDeploymentResource = tenantDeploymentResourceModelFactory.create(tenantId,
                tenantStageId,
                tenantVersionId);

        final var request = new SyncTenantDeploymentResourceRequest(tenantDeploymentResource);
        return tenantShard.getService().execute(request)
                .replaceWith(tenantDeploymentResource);
    }
}

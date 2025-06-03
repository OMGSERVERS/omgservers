package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployment;

import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperResponse;
import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceConfigDto;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.SyncTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.shard.tenant.tenantImage.ViewTenantImagesResponse;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.shard.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantDeploymentResourceModelFactory;
import com.omgservers.service.operation.authz.AuthorizeTenantStageRequestOperation;
import com.omgservers.service.operation.security.GetSecurityAttributeOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateDeploymentMethodImpl implements CreateDeploymentMethod {

    final TenantShard tenantShard;

    final AuthorizeTenantStageRequestOperation authorizeTenantStageRequestOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    final TenantDeploymentResourceModelFactory tenantDeploymentResourceModelFactory;

    @Override
    public Uni<CreateDeploymentDeveloperResponse> execute(final CreateDeploymentDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var project = request.getProject();
        final var stage = request.getStage();
        final var userId = getSecurityAttributeOperation.getUserId();
        final var permission = TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER;

        return authorizeTenantStageRequestOperation.execute(tenant, project, stage, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    final var tenantStageId = authorization.tenantStageId();
                    final var tenantVersionId = request.getVersionId();
                    final DeploymentConfigDto deploymentConfig;
                    if (Objects.isNull(request.getConfig())) {
                        deploymentConfig = new DeploymentConfigDto();
                    } else {
                        deploymentConfig = request.getConfig();
                    }
                    return createDeployment(tenantId, tenantStageId, tenantVersionId, deploymentConfig);
                })
                .map(CreateDeploymentDeveloperResponse::new);
    }

    Uni<Long> createDeployment(final Long tenantId,
                               final Long tenantStageId,
                               final Long tenantVersionId,
                               final DeploymentConfigDto deploymentConfig) {
        return verifyAtLeastOneTenantImageExists(tenantId, tenantVersionId)
                .flatMap(voidItem -> getTenantVersion(tenantId, tenantVersionId))
                .flatMap(tenantVersion -> getTenantStage(tenantId, tenantStageId)
                        .flatMap(tenantStage -> {
                            final var versionProjectId = tenantVersion.getProjectId();
                            final var stageProjectId = tenantStage.getProjectId();
                            // Version and stage must belong to the same project
                            if (versionProjectId.equals(stageProjectId)) {
                                return createTenantDeploymentResource(tenantId,
                                        tenantStageId,
                                        tenantVersionId,
                                        deploymentConfig)
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
                                                                      final Long tenantVersionId,
                                                                      final DeploymentConfigDto deploymentConfig) {
        final var tenantDeploymentResourceConfigDto = new TenantDeploymentResourceConfigDto();
        tenantDeploymentResourceConfigDto.setDeploymentConfig(deploymentConfig);

        final var tenantDeploymentResource = tenantDeploymentResourceModelFactory.create(tenantId,
                tenantStageId,
                tenantVersionId,
                tenantDeploymentResourceConfigDto);

        final var request = new SyncTenantDeploymentResourceRequest(tenantDeploymentResource);
        return tenantShard.getService().execute(request)
                .replaceWith(tenantDeploymentResource);
    }
}

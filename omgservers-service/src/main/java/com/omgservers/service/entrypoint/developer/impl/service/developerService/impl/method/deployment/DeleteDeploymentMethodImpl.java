package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployment;

import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperResponse;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantStagePermissionOperation;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.operation.authz.AuthorizeDeploymentRequestOperation;
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
class DeleteDeploymentMethodImpl implements DeleteDeploymentMethod {

    final TenantShard tenantShard;

    final AuthorizeDeploymentRequestOperation authorizeDeploymentRequestOperation;

    final CheckTenantStagePermissionOperation checkTenantStagePermissionOperation;
    final GetIdByTenantOperation getIdByTenantOperation;


    final TenantVersionModelFactory tenantVersionModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteDeploymentDeveloperResponse> execute(final DeleteDeploymentDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var deploymentId = request.getDeploymentId();
        final var userId = securityIdentity.<Long>getAttribute(
                SecurityAttributesEnum.USER_ID.getAttributeName());
        final var permission = TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER;
        return authorizeDeploymentRequestOperation.execute(deploymentId, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    return findTenantDeploymentResource(tenantId, deploymentId)
                            .flatMap(tenantDeploymentResource -> {
                                final var tenantDeploymentResourceId = tenantDeploymentResource.getId();
                                return deleteTenantDeploymentResource(tenantId, tenantDeploymentResourceId)
                                        .invoke(deleted -> {
                                            if (deleted) {
                                                log.info("Deleted deployment \"{}\" in tenant \"{}\"",
                                                        deploymentId, tenantId);
                                            }
                                        });
                            });
                })
                .map(DeleteDeploymentDeveloperResponse::new);
    }

    Uni<TenantDeploymentResourceModel> findTenantDeploymentResource(final Long tenantId,
                                                                    final Long deploymentId) {
        final var request = new FindTenantDeploymentResourceRequest(tenantId, deploymentId);
        return tenantShard.getService().execute(request)
                .map(FindTenantDeploymentResourceResponse::getTenantDeploymentResource);
    }

    Uni<Boolean> deleteTenantDeploymentResource(final Long tenantId,
                                                final Long id) {
        final var request = new DeleteTenantDeploymentResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantDeploymentResourceResponse::getDeleted);
    }
}

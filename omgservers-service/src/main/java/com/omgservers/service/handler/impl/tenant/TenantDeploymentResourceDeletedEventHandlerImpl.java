package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.shard.deployment.deployment.DeleteDeploymentRequest;
import com.omgservers.schema.shard.deployment.deployment.DeleteDeploymentResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.GetTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.GetTenantDeploymentResourceResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentResourceDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantDeploymentResourceDeletedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final TenantShard tenantShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DEPLOYMENT_RESOURCE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantDeploymentResourceDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantDeploymentResource(tenantId, id)
                .flatMap(tenantDeploymentResource -> {
                    log.debug("Deleted, {}", tenantDeploymentResource);

                    final var deploymentId = tenantDeploymentResource.getDeploymentId();
                    return deleteDeployment(deploymentId);
                })
                .replaceWithVoid();
    }

    Uni<TenantDeploymentResourceModel> getTenantDeploymentResource(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantDeploymentResourceResponse::getTenantDeploymentResource);
    }

    Uni<Boolean> deleteDeployment(final Long deploymentId) {
        final var request = new DeleteDeploymentRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(DeleteDeploymentResponse::getDeleted);
    }
}

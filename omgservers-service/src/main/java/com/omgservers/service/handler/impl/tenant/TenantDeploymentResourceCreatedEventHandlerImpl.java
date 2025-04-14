package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.shard.deployment.deployment.SyncDeploymentRequest;
import com.omgservers.schema.shard.deployment.deployment.SyncDeploymentResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.GetTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.GetTenantDeploymentResourceResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentResourceCreatedEventBodyModel;
import com.omgservers.service.factory.deployment.DeploymentModelFactory;
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
public class TenantDeploymentResourceCreatedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final TenantShard tenantShard;

    final DeploymentModelFactory deploymentModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DEPLOYMENT_RESOURCE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantDeploymentResourceCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getTenantDeploymentResource(tenantId, id)
                .flatMap(tenantDeploymentResource -> {
                    log.debug("Created, {}", tenantDeploymentResource);

                    return createDeployment(tenantDeploymentResource, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<TenantDeploymentResourceModel> getTenantDeploymentResource(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantDeploymentResourceResponse::getTenantDeploymentResource);
    }

    Uni<Boolean> createDeployment(final TenantDeploymentResourceModel tenantDeploymentResource,
                                  final String idempotencyKey) {
        final var deploymentId = tenantDeploymentResource.getDeploymentId();
        final var tenantId = tenantDeploymentResource.getTenantId();
        final var tenantStageId = tenantDeploymentResource.getStageId();
        final var tenantVersionId = tenantDeploymentResource.getVersionId();
        final var lobby = deploymentModelFactory.create(deploymentId,
                tenantId,
                tenantStageId,
                tenantVersionId,
                DeploymentConfigDto.create(),
                idempotencyKey);
        final var request = new SyncDeploymentRequest(lobby);
        return deploymentShard.getService().executeWithIdempotency(request)
                .map(SyncDeploymentResponse::getCreated);
    }
}

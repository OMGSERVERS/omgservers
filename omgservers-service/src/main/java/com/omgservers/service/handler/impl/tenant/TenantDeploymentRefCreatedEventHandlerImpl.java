package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.GetTenantDeploymentRefRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.GetTenantDeploymentRefResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.FindTenantDeploymentResourceResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentRefCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantDeploymentRefCreatedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DEPLOYMENT_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantDeploymentRefCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantDeploymentRef(tenantId, id)
                .flatMap(tenantDeploymentRef -> {
                    log.debug("Created, {}", tenantDeploymentRef);

                    final var deploymentId = tenantDeploymentRef.getDeploymentId();
                    return findTenantDeploymentResource(tenantId, deploymentId)
                            .flatMap(tenantDeploymentResource -> {
                                final var tenantDeploymentResourceId = tenantDeploymentResource.getId();
                                return updateTenantDeploymentResourceStatus(tenantId, tenantDeploymentResourceId);
                            });
                })
                .replaceWithVoid();
    }

    Uni<TenantDeploymentRefModel> getTenantDeploymentRef(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRefRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantDeploymentRefResponse::getTenantDeploymentRef);
    }

    Uni<TenantDeploymentResourceModel> findTenantDeploymentResource(final Long tenantId,
                                                                    final Long deploymentId) {
        final var request = new FindTenantDeploymentResourceRequest(tenantId, deploymentId);
        return tenantShard.getService().execute(request)
                .map(FindTenantDeploymentResourceResponse::getTenantDeploymentResource);
    }

    Uni<Boolean> updateTenantDeploymentResourceStatus(final Long tenantId,
                                                      final Long id) {
        final var request = new UpdateTenantDeploymentResourceStatusRequest(tenantId, id,
                TenantDeploymentResourceStatusEnum.CREATED);
        return tenantShard.getService().execute(request)
                .map(UpdateTenantDeploymentResourceStatusResponse::getUpdated);
    }
}

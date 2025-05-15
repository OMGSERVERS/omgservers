package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.GetTenantDeploymentRefRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentRef.GetTenantDeploymentRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentRefDeletedEventBodyModel;
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
public class TenantDeploymentRefDeletedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DEPLOYMENT_REF_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantDeploymentRefDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantDeploymentRef(tenantId, id)
                .flatMap(tenantDeploymentRef -> {
                    log.debug("Deleted, {}", tenantDeploymentRef);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantDeploymentRefModel> getTenantDeploymentRef(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRefRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantDeploymentRefResponse::getTenantDeploymentRef);
    }
}

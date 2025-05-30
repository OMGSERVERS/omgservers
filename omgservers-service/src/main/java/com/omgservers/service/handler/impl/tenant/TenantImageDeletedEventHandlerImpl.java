package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.shard.tenant.tenantImage.GetTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.GetTenantImageResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantImageDeletedEventBodyModel;
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
public class TenantImageDeletedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_IMAGE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantImageDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantImage(tenantId, id)
                .flatMap(tenantImage -> {
                    log.debug("Deleted, {}", tenantImage);

                    // TODO: clean up docker registry
                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantImageModel> getTenantImage(final Long tenantId, final Long id) {
        final var request = new GetTenantImageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantImageResponse::getTenantImage);
    }
}

package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.module.tenant.tenantImage.GetTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.GetTenantImageResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantImageCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantImageCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_IMAGE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantImageCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantImage(tenantId, id)
                .flatMap(tenantImage -> {
                    final var tenantVersionId = tenantImage.getVersionId();
                    final var qualifier = tenantImage.getQualifier();
                    final var imageId = tenantImage.getImageId();
                    log.info("Tenant image was created, id={}, tenantVersion={}/{}, qualifier={}, imageId={}",
                            tenantImage.getId(),
                            tenantId,
                            tenantVersionId,
                            qualifier,
                            imageId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantImageModel> getTenantImage(final Long tenantId, final Long id) {
        final var request = new GetTenantImageRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantImage(request)
                .map(GetTenantImageResponse::getTenantImage);
    }
}
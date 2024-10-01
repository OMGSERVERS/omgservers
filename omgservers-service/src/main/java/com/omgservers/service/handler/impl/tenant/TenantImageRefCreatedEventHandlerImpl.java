package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import com.omgservers.schema.module.tenant.tenantImageRef.GetTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.GetTenantImageRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantImageRefCreatedEventBodyModel;
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
public class TenantImageRefCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_IMAGE_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantImageRefCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantImageRef(tenantId, id)
                .flatMap(tenantImageRef -> {
                    final var versionId = tenantImageRef.getVersionId();
                    final var qualifier = tenantImageRef.getQualifier();
                    final var imageId = tenantImageRef.getImageId();
                    log.info("Tenant image ref was created, id={}, version={}/{}, qualifier={}, imageId={}",
                            tenantImageRef.getId(),
                            tenantId,
                            versionId,
                            qualifier,
                            imageId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantImageRefModel> getTenantImageRef(final Long tenantId, final Long id) {
        final var request = new GetTenantImageRefRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantImageRef(request)
                .map(GetTenantImageRefResponse::getTenantImageRef);
    }
}

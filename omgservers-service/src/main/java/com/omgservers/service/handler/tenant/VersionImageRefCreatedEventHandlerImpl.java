package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.versionImageRef.GetVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.GetVersionImageRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.VersionImageRefCreatedEventBodyModel;
import com.omgservers.schema.model.versionImageRef.VersionImageRefModel;
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
public class VersionImageRefCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_IMAGE_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionImageRefCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionImageRef(tenantId, id)
                .flatMap(versionImageRef -> {
                    final var versionId = versionImageRef.getVersionId();
                    final var qualifier = versionImageRef.getQualifier();
                    final var imageId = versionImageRef.getImageId();
                    log.info("Version image ref was created, id={}, version={}/{}, qualifier={}, imageId={}",
                            versionImageRef.getId(),
                            tenantId,
                            versionId,
                            qualifier,
                            imageId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<VersionImageRefModel> getVersionImageRef(final Long tenantId, final Long id) {
        final var request = new GetVersionImageRefRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionImageRef(request)
                .map(GetVersionImageRefResponse::getVersionImageRefModel);
    }
}

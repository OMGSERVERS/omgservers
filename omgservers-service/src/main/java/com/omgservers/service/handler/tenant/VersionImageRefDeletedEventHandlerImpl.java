package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.versionImageRef.GetVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.GetVersionImageRefResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.VersionImageRefDeletedEventBodyModel;
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
public class VersionImageRefDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_IMAGE_REF_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionImageRefDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionImageRef(tenantId, id)
                .flatMap(versionImageRef -> {
                    final var versionId = versionImageRef.getVersionId();
                    final var qualifier = versionImageRef.getQualifier();
                    log.info("Version image ref was deleted, id={}, version={}/{}, qualifier={}",
                            versionImageRef.getId(),
                            tenantId,
                            versionId,
                            qualifier);

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

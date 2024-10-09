package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantVersionCreatedEventBodyModel;
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
public class TenantVersionCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_VERSION_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantVersionCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantVersion(tenantId, id)
                .flatMap(tenantVersion -> {
                    log.info("Tenant version was created, " +
                                    "tenantVersion={}/{}, tenantStageId={}, modes={}",
                            tenantId,
                            id,
                            tenantVersion.getProjectId(),
                            tenantVersion.getConfig().getModes().stream().map(TenantVersionModeDto::getName).toList());

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }
}

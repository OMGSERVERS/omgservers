package com.omgservers.service.handler;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.TenantDeletedEventBodyModel;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DELETED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getId();

        return tenantModule.getShortcutService().getTenant(tenantId)
                .flatMap(tenant -> {
                    log.info("Tenant was deleted, tenantId={}", tenantId);

                    return tenantModule.getShortcutService().deleteTenantPermissions(tenantId)
                            .flatMap(voidItem -> tenantModule.getShortcutService().deleteProjects(tenantId));
                })
                .replaceWith(true);
    }
}

package com.omgservers.service.handler.tenant;

import com.omgservers.model.entitiy.EntityQualifierEnum;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.service.factory.EntityModelFactory;
import com.omgservers.service.module.system.SystemModule;
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
public class TenantCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final SystemModule systemModule;

    final EntityModelFactory entityModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getId();

        return tenantModule.getShortcutService().getTenant(tenantId)
                .flatMap(tenant -> {
                    log.info("Tenant was created, tenant={}", tenantId);

                    final var entity = entityModelFactory.create(tenantId, EntityQualifierEnum.TENANT);
                    return systemModule.getShortcutService().syncEntity(entity);
                })
                .replaceWith(true);
    }
}

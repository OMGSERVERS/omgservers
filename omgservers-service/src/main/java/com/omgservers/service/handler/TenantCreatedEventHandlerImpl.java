package com.omgservers.service.handler;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantCreatedEventHandlerImpl implements EventHandler {

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (TenantCreatedEventBodyModel) event.getBody();
        final var id = body.getId();

        log.info("Tenant was created, tenantId={}", id);
        return Uni.createFrom().item(true);
    }
}

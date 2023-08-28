package com.omgservers.handler;

import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.operation.getServers.GetServersOperation;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.TenantCreatedEventBodyModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantCreatedEventHandlerImpl implements EventHandler {

    final InternalModule internalModule;

    final GetServersOperation getServersOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (TenantCreatedEventBodyModel) event.getBody();
        return Uni.createFrom().item(true);
    }
}

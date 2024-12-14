package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRequestDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantMatchmakerRequestDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_MATCHMAKER_REQUEST_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantMatchmakerRequestDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantMatchmakerRequest(tenantId, id)
                .flatMap(tenantMatchmakerRequest -> {
                    log.debug("Deleted, {}", tenantMatchmakerRequest);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantMatchmakerRequestModel> getTenantMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getService().getTenantMatchmakerRequest(request)
                .map(GetTenantMatchmakerRequestResponse::getTenantMatchmakerRequest);
    }
}

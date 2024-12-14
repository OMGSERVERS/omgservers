package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.GetTenantMatchmakerRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
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
public class TenantMatchmakerRequestCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    final MatchmakerModelFactory matchmakerModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_MATCHMAKER_REQUEST_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantMatchmakerRequestCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantMatchmakerRequest(tenantId, id)
                .flatMap(tenantMatchmakerRequest -> {
                    log.debug("Created, {}", tenantMatchmakerRequest);

                    return syncMatchmaker(tenantMatchmakerRequest, event.getIdempotencyKey());
                })
                .replaceWithVoid();
    }

    Uni<TenantMatchmakerRequestModel> getTenantMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getService().getTenantMatchmakerRequest(request)
                .map(GetTenantMatchmakerRequestResponse::getTenantMatchmakerRequest);
    }

    Uni<Boolean> syncMatchmaker(final TenantMatchmakerRequestModel tenantMatchmakerRequest,
                                final String idempotencyKey) {
        final var tenantId = tenantMatchmakerRequest.getTenantId();
        final var deploymentId = tenantMatchmakerRequest.getDeploymentId();
        final var matchmakerId = tenantMatchmakerRequest.getMatchmakerId();
        final var matchmaker = matchmakerModelFactory.create(matchmakerId,
                tenantId,
                deploymentId,
                idempotencyKey);
        final var request = new SyncMatchmakerRequest(matchmaker);
        return matchmakerModule.getService().executeWithIdempotency(request)
                .map(SyncMatchmakerResponse::getCreated);
    }
}

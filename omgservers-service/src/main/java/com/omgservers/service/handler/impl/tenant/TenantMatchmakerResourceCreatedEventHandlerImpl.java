package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.GetTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.GetTenantMatchmakerResourceResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerResourceCreatedEventBodyModel;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantMatchmakerResourceCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final TenantShard tenantShard;

    final MatchmakerModelFactory matchmakerModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_MATCHMAKER_RESOURCE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantMatchmakerResourceCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantMatchmakerResource(tenantId, id)
                .flatMap(tenantMatchmakerResource -> {
                    log.debug("Created, {}", tenantMatchmakerResource);

                    return syncMatchmaker(tenantMatchmakerResource, event.getIdempotencyKey());
                })
                .replaceWithVoid();
    }

    Uni<TenantMatchmakerResourceModel> getTenantMatchmakerResource(final Long tenantId, final Long id) {
        final var request = new GetTenantMatchmakerResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantMatchmakerResourceResponse::getTenantMatchmakerResource);
    }

    Uni<Boolean> syncMatchmaker(final TenantMatchmakerResourceModel tenantMatchmakerResource,
                                final String idempotencyKey) {
        final var tenantId = tenantMatchmakerResource.getTenantId();
        final var deploymentId = tenantMatchmakerResource.getDeploymentId();
        final var matchmakerId = tenantMatchmakerResource.getMatchmakerId();
        final var matchmaker = matchmakerModelFactory.create(matchmakerId,
                tenantId,
                deploymentId,
                idempotencyKey);
        final var request = new SyncMatchmakerRequest(matchmaker);
        return matchmakerShard.getService().executeWithIdempotency(request)
                .map(SyncMatchmakerResponse::getCreated);
    }
}

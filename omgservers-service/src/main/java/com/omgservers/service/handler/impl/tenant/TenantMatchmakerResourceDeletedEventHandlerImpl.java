package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.GetTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.GetTenantMatchmakerResourceResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerResourceDeletedEventBodyModel;
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
public class TenantMatchmakerResourceDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final TenantShard tenantShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_MATCHMAKER_RESOURCE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantMatchmakerResourceDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantMatchmakerResource(tenantId, id)
                .flatMap(tenantMatchmakerResource -> {
                    log.debug("Deleted, {}", tenantMatchmakerResource);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<TenantMatchmakerResourceModel> getTenantMatchmakerResource(final Long tenantId, final Long id) {
        final var request = new GetTenantMatchmakerResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantMatchmakerResourceResponse::getTenantMatchmakerResource);
    }
}

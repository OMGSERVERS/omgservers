package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.GetVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.GetVersionMatchmakerRequestResponse;
import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.module.tenant.VersionMatchmakerRequestDeletedEventBodyModel;
import com.omgservers.schema.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
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
public class VersionMatchmakerRequestDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_MATCHMAKER_REQUEST_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionMatchmakerRequestDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionMatchmakerRequest(tenantId, id)
                .flatMap(versionMatchmakerRequest -> {
                    final var matchmakerId = versionMatchmakerRequest.getMatchmakerId();

                    log.info("Version matchmaker request was deleted, id={}, versionId={}, matchmakerId={}",
                            versionMatchmakerRequest.getId(), tenantId, matchmakerId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<VersionMatchmakerRequestModel> getVersionMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new GetVersionMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionMatchmakerRequest(request)
                .map(GetVersionMatchmakerRequestResponse::getVersionMatchmakerRequest);
    }
}

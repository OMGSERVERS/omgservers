package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequestResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.service.factory.MatchmakerModelFactory;
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
public class VersionMatchmakerRequestCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    final MatchmakerModelFactory matchmakerModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_MATCHMAKER_REQUEST_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionMatchmakerRequestCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionMatchmakerRequest(tenantId, id)
                .flatMap(versionMatchmakerRequest -> {
                    final var versionId = versionMatchmakerRequest.getVersionId();
                    final var matchmakerId = versionMatchmakerRequest.getMatchmakerId();
                    log.info("Version matchmaker request was created, id={}, version={}/{}, matchmakerId={}",
                            versionMatchmakerRequest.getId(),
                            tenantId,
                            versionId,
                            matchmakerId);

                    return syncMatchmaker(versionMatchmakerRequest);
                })
                .replaceWithVoid();
    }

    Uni<VersionMatchmakerRequestModel> getVersionMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new GetVersionMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionMatchmakerRequest(request)
                .map(GetVersionMatchmakerRequestResponse::getVersionMatchmakerRequest);
    }

    Uni<Boolean> syncMatchmaker(final VersionMatchmakerRequestModel versionMatchmakerRequest) {
        final var tenantId = versionMatchmakerRequest.getTenantId();
        final var versionId = versionMatchmakerRequest.getVersionId();
        final var matchmakerId = versionMatchmakerRequest.getMatchmakerId();
        final var matchmaker = matchmakerModelFactory.create(matchmakerId,
                tenantId,
                versionId,
                versionMatchmakerRequest.getIdempotencyKey());
        final var request = new SyncMatchmakerRequest(matchmaker);
        return matchmakerModule.getMatchmakerService().syncMatchmaker(request)
                .map(SyncMatchmakerResponse::getCreated);
    }
}

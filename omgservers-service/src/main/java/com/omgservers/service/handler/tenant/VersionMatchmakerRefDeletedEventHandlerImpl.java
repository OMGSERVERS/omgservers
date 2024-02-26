package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.tenant.GetVersionMatchmakerRefRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRefResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionMatchmakerRefDeletedEventBodyModel;
import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionMatchmakerRefDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_MATCHMAKER_REF_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionMatchmakerRefDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getVersionMatchmakerRef(tenantId, id)
                .flatMap(versionMatchmakerRef -> {
                    final var versionId = versionMatchmakerRef.getVersionId();
                    final var matchmakerId = versionMatchmakerRef.getMatchmakerId();
                    log.info("Version matchmaker ref was deleted, id={}, version={}/{}, matchmakerId={}",
                            versionMatchmakerRef.getId(),
                            tenantId,
                            versionId,
                            matchmakerId);

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<VersionMatchmakerRefModel> getVersionMatchmakerRef(final Long tenantId, final Long id) {
        final var request = new GetVersionMatchmakerRefRequest(tenantId, id);
        return tenantModule.getVersionService().getVersionMatchmakerRef(request)
                .map(GetVersionMatchmakerRefResponse::getVersionMatchmakerRef);
    }
}

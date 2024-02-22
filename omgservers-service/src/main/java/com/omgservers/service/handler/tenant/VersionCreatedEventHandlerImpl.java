package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.SyncVersionLobbyRequestResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequestResponse;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRequestsRequest;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRequestsResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRequestsRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRequestsResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.VersionCreatedEventBodyModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.service.factory.VersionLobbyRequestModelFactory;
import com.omgservers.service.factory.VersionMatchmakerRequestModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final VersionMatchmakerRequestModelFactory versionMatchmakerRequestModelFactory;
    final VersionLobbyRequestModelFactory versionLobbyRequestModelFactory;


    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var versionId = body.getId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    log.info("Version was created, version={}/{}, stageId={}, modes={}, files={}",
                            tenantId,
                            versionId,
                            version.getStageId(),
                            version.getConfig().getModes().stream().map(VersionModeModel::getName).toList(),
                            version.getSourceCode().getFiles().size());

                    return initVersionLobby(tenantId, versionId)
                            .flatMap(created -> initVersionMatchmaker(tenantId, versionId));
                })
                .replaceWithVoid();
    }

    Uni<VersionModel> getVersion(final Long tenantId, final Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Void> initVersionLobby(final Long tenantId, final Long versionId) {
        return viewVersionLobbyRequest(tenantId, versionId)
                .flatMap(versionLobbyRequests -> {
                    if (versionLobbyRequests.isEmpty()) {
                        return syncVersionLobbyRequest(tenantId, versionId)
                                .replaceWithVoid();
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<List<VersionLobbyRequestModel>> viewVersionLobbyRequest(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionLobbyRequestsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionLobbyRequests(request)
                .map(ViewVersionLobbyRequestsResponse::getVersionLobbyRequests);
    }

    Uni<Boolean> syncVersionLobbyRequest(final Long tenantId, final Long versionId) {
        final var versionLobbyRequest = versionLobbyRequestModelFactory.create(tenantId, versionId);
        final var request = new SyncVersionLobbyRequestRequest(versionLobbyRequest);
        return tenantModule.getVersionService().syncVersionLobbyRequest(request)
                .map(SyncVersionLobbyRequestResponse::getCreated);
    }

    Uni<Void> initVersionMatchmaker(final Long tenantId, final Long versionId) {
        return viewVersionMatchmakerRequests(tenantId, versionId)
                .flatMap(versionMatchmakerRequests -> {
                    if (versionMatchmakerRequests.isEmpty()) {
                        return syncVersionMatchmakerRequest(tenantId, versionId)
                                .replaceWithVoid();
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<List<VersionMatchmakerRequestModel>> viewVersionMatchmakerRequests(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionMatchmakerRequestsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionMatchmakerRequests(request)
                .map(ViewVersionMatchmakerRequestsResponse::getVersionMatchmakerRequests);
    }

    Uni<Boolean> syncVersionMatchmakerRequest(final Long tenantId, final Long versionId) {
        final var versionMatchmaker = versionMatchmakerRequestModelFactory.create(tenantId, versionId);
        final var request = new SyncVersionMatchmakerRequestRequest(versionMatchmaker);
        return tenantModule.getVersionService().syncVersionMatchmakerRequest(request)
                .map(SyncVersionMatchmakerRequestResponse::getCreated);
    }
}

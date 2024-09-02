package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.DeleteVersionLobbyRequestResponse;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.DeleteVersionMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsResponse;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRequestsResponse;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRefsResponse;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRequestsResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.VersionDeletedEventBodyModel;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.schema.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var versionId = body.getId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    final var stageId = version.getStageId();
                    log.info("Version was deleted, version={}/{}, stageId={}",
                            tenantId,
                            versionId,
                            stageId);

                    return deleteVersionLobbyRequests(tenantId, versionId)
                            .flatMap(voidItem -> deleteVersionLobbies(tenantId, versionId))
                            .flatMap(voidItem -> deleteVersionMatchmakerRequests(tenantId, versionId))
                            .flatMap(voidItem -> deleteVersionMatchmakers(tenantId, versionId));
                })
                .replaceWithVoid();
    }

    Uni<VersionModel> getVersion(final Long tenantId, final Long versionId) {
        final var request = new GetVersionRequest(tenantId, versionId);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Void> deleteVersionLobbyRequests(final Long tenantId, final Long versionId) {
        return viewVersionLobbyRequests(tenantId, versionId)
                .flatMap(versionLobbyRequests -> Multi.createFrom().iterable(versionLobbyRequests)
                        .onItem().transformToUniAndConcatenate(versionLobbyRequest ->
                                deleteVersionLobbyRequest(tenantId, versionLobbyRequest.getId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<VersionLobbyRequestModel>> viewVersionLobbyRequests(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionLobbyRequestsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionLobbyRequests(request)
                .map(ViewVersionLobbyRequestsResponse::getVersionLobbyRequests);
    }

    Uni<Boolean> deleteVersionLobbyRequest(final Long tenantId, final Long id) {
        final var request = new DeleteVersionLobbyRequestRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersionLobbyRequest(request)
                .map(DeleteVersionLobbyRequestResponse::getDeleted);
    }

    Uni<Void> deleteVersionLobbies(final Long tenantId, final Long versionId) {
        return viewVersionLobbyRefs(tenantId, versionId)
                .flatMap(versionLobbyRefs -> Multi.createFrom().iterable(versionLobbyRefs)
                        .onItem().transformToUniAndConcatenate(versionLobbyRef ->
                                deleteLobby(versionLobbyRef.getLobbyId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<VersionLobbyRefModel>> viewVersionLobbyRefs(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionLobbyRefsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionLobbyRefs(request)
                .map(ViewVersionLobbyRefsResponse::getVersionLobbyRefs);
    }

    Uni<Boolean> deleteLobby(final Long lobbyId) {
        final var request = new DeleteLobbyRequest(lobbyId);
        return lobbyModule.getLobbyService().deleteLobby(request)
                .map(DeleteLobbyResponse::getDeleted);
    }

    Uni<Void> deleteVersionMatchmakerRequests(final Long tenantId, final Long versionId) {
        return viewVersionMatchmakerRequests(tenantId, versionId)
                .flatMap(versionMatchmakerRequests -> Multi.createFrom().iterable(versionMatchmakerRequests)
                        .onItem().transformToUniAndConcatenate(versionMatchmakerRequest ->
                                deleteVersionMatchmakerRequest(tenantId, versionMatchmakerRequest.getId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<VersionMatchmakerRequestModel>> viewVersionMatchmakerRequests(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionMatchmakerRequestsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionMatchmakerRequests(request)
                .map(ViewVersionMatchmakerRequestsResponse::getVersionMatchmakerRequests);
    }

    Uni<Boolean> deleteVersionMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new DeleteVersionMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersionMatchmakerRequest(request)
                .map(DeleteVersionMatchmakerRequestResponse::getDeleted);
    }

    Uni<Void> deleteVersionMatchmakers(final Long tenantId, final Long versionId) {
        return viewVersionMatchmakerRefs(tenantId, versionId)
                .flatMap(versionMatchmakerRefs -> Multi.createFrom().iterable(versionMatchmakerRefs)
                        .onItem().transformToUniAndConcatenate(versionMatchmakerRef ->
                                deleteMatchmaker(versionMatchmakerRef.getMatchmakerId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<VersionMatchmakerRefModel>> viewVersionMatchmakerRefs(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionMatchmakerRefsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionMatchmakerRefs(request)
                .map(ViewVersionMatchmakerRefsResponse::getVersionMatchmakerRefs);
    }

    Uni<Boolean> deleteMatchmaker(final Long matchmakerId) {
        final var request = new DeleteMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().deleteMatchmaker(request)
                .map(DeleteMatchmakerResponse::getDeleted);
    }
}

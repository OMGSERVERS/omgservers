package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantVersionDeletedEventBodyModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
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
public class TenantVersionDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_VERSION_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantVersionDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var versionId = body.getId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    final var stageId = version.getProjectId();
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

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long versionId) {
        final var request = new GetTenantVersionRequest(tenantId, versionId);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Void> deleteVersionLobbyRequests(final Long tenantId, final Long versionId) {
        return viewVersionLobbyRequests(tenantId, versionId)
                .flatMap(versionLobbyRequests -> Multi.createFrom().iterable(versionLobbyRequests)
                        .onItem().transformToUniAndConcatenate(versionLobbyRequest ->
                                deleteVersionLobbyRequest(tenantId, versionLobbyRequest.getId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantLobbyRequestModel>> viewVersionLobbyRequests(final Long tenantId, final Long versionId) {
        final var request = new ViewTenantLobbyRequestsRequest(tenantId, versionId);
        return tenantModule.getTenantService().viewVersionLobbyRequests(request)
                .map(ViewTenantLobbyRequestsResponse::getTenantLobbyRequests);
    }

    Uni<Boolean> deleteVersionLobbyRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantLobbyRequestRequest(tenantId, id);
        return tenantModule.getTenantService().deleteVersionLobbyRequest(request)
                .map(DeleteTenantLobbyRequestResponse::getDeleted);
    }

    Uni<Void> deleteVersionLobbies(final Long tenantId, final Long versionId) {
        return viewVersionLobbyRefs(tenantId, versionId)
                .flatMap(versionLobbyRefs -> Multi.createFrom().iterable(versionLobbyRefs)
                        .onItem().transformToUniAndConcatenate(versionLobbyRef ->
                                deleteLobby(versionLobbyRef.getLobbyId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantLobbyRefModel>> viewVersionLobbyRefs(final Long tenantId, final Long versionId) {
        final var request = new ViewTenantLobbyRefsRequest(tenantId, versionId);
        return tenantModule.getTenantService().viewVersionLobbyRefs(request)
                .map(ViewTenantLobbyRefsResponse::getTenantLobbyRefs);
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

    Uni<List<TenantMatchmakerRequestModel>> viewVersionMatchmakerRequests(final Long tenantId, final Long versionId) {
        final var request = new ViewTenantMatchmakerRequestsRequest(tenantId, versionId);
        return tenantModule.getTenantService().viewVersionMatchmakerRequests(request)
                .map(ViewTenantMatchmakerRequestsResponse::getTenantMatchmakerRequests);
    }

    Uni<Boolean> deleteVersionMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getTenantService().deleteVersionMatchmakerRequest(request)
                .map(DeleteTenantMatchmakerRequestResponse::getDeleted);
    }

    Uni<Void> deleteVersionMatchmakers(final Long tenantId, final Long versionId) {
        return viewVersionMatchmakerRefs(tenantId, versionId)
                .flatMap(versionMatchmakerRefs -> Multi.createFrom().iterable(versionMatchmakerRefs)
                        .onItem().transformToUniAndConcatenate(versionMatchmakerRef ->
                                deleteMatchmaker(versionMatchmakerRef.getMatchmakerId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantMatchmakerRefModel>> viewVersionMatchmakerRefs(final Long tenantId, final Long versionId) {
        final var request = new ViewTenantMatchmakerRefsRequest(tenantId, versionId);
        return tenantModule.getTenantService().viewVersionMatchmakerRefs(request)
                .map(ViewTenantMatchmakerRefsResponse::getTenantMatchmakerRefs);
    }

    Uni<Boolean> deleteMatchmaker(final Long matchmakerId) {
        final var request = new DeleteMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().deleteMatchmaker(request)
                .map(DeleteMatchmakerResponse::getDeleted);
    }
}

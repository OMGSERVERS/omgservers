package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerMatchClientResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeClientRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeClientResponse;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.MatchmakerMatchClientDeletedEventBodyModel;
import com.omgservers.model.lobby.LobbyModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.RuntimeClientModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerMatchClientDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final ClientModule clientModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final RuntimeClientModelFactory runtimeClientModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_CLIENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerMatchClientDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var id = body.getId();

        return getMatchClient(matchmakerId, id)
                .flatMap(matchClient -> {
                    final var matchId = matchClient.getMatchId();
                    final var clientId = matchClient.getClientId();

                    log.info("Matchmaker match client was deleted, matchClient={}/{}, clientId={}, matchId={}",
                            matchmakerId, id, clientId, matchId);

                    return getClient(clientId)
                            .flatMap(client -> {
                                if (client.getDeleted()) {
                                    log.warn("Client was already deleted, clientId={}", clientId);
                                    return Uni.createFrom().voidItem();
                                }

                                final var tenantId = client.getTenantId();
                                final var versionId = client.getVersionId();

                                return selectVersionLobbyRef(tenantId, versionId)
                                        .flatMap(versionLobbyRef -> {
                                            final var lobbyId = versionLobbyRef.getLobbyId();
                                            return getLobby(lobbyId)
                                                    .flatMap(lobby -> {
                                                        final var runtimeId = lobby.getRuntimeId();

                                                        final var idempotencyKey = event.getIdempotencyKey();
                                                        return syncRuntimeClient(runtimeId, clientId, idempotencyKey);
                                                    });
                                        });
                            });
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerMatchClientModel> getMatchClient(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerMatchClientRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().getMatchmakerMatchClient(request)
                .map(GetMatchmakerMatchClientResponse::getMatchClient);
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<VersionLobbyRefModel> selectVersionLobbyRef(final Long tenantId, final Long versionId) {
        return viewVersionLobbyRefs(tenantId, versionId)
                .map(refs -> {
                    if (refs.isEmpty()) {
                        throw new ServerSideNotFoundException(ExceptionQualifierEnum.LOBBY_NOT_FOUND,
                                String.format("lobby was not selected, version=%d/%d", tenantId, versionId));
                    } else {
                        final var randomRefIndex = ThreadLocalRandom.current().nextInt(refs.size()) % refs.size();
                        final var randomLobbyRef = refs.get(randomRefIndex);
                        return randomLobbyRef;
                    }
                });
    }

    Uni<List<VersionLobbyRefModel>> viewVersionLobbyRefs(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionLobbyRefsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionLobbyRefs(request)
                .map(ViewVersionLobbyRefsResponse::getVersionLobbyRefs);
    }

    Uni<LobbyModel> getLobby(final Long lobbyId) {
        final var request = new GetLobbyRequest(lobbyId);
        return lobbyModule.getLobbyService().getLobby(request)
                .map(GetLobbyResponse::getLobby);
    }

    Uni<Boolean> syncRuntimeClient(final Long runtimeId,
                                   final Long clientId,
                                   final String idempotencyKey) {
        final var runtimeClient = runtimeClientModelFactory.create(runtimeId, clientId, idempotencyKey);
        final var request = new SyncRuntimeClientRequest(runtimeClient);
        return runtimeModule.getRuntimeService().syncRuntimeClient(request)
                .map(SyncRuntimeClientResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", runtimeClient, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}

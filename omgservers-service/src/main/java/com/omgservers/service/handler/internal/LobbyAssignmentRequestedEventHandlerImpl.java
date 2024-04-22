package com.omgservers.service.handler.internal;

import com.omgservers.model.dto.lobby.GetLobbyRequest;
import com.omgservers.model.dto.lobby.GetLobbyResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeAssignmentResponse;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.internal.LobbyAssignmentRequestedEventBodyModel;
import com.omgservers.model.lobby.LobbyModel;
import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
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
public class LobbyAssignmentRequestedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.LOBBY_ASSIGNMENT_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (LobbyAssignmentRequestedEventBodyModel) event.getBody();
        final var clientId = body.getClientId();
        final var tenantId = body.getTenantId();
        final var versionId = body.getVersionId();

        return selectVersionLobbyRef(tenantId, versionId)
                .flatMap(versionLobbyRef -> {
                    final var lobbyId = versionLobbyRef.getLobbyId();
                    return getLobby(lobbyId)
                            .flatMap(lobby -> {
                                final var runtimeId = lobby.getRuntimeId();
                                return syncRuntimeAssignment(runtimeId, clientId, event.getIdempotencyKey());
                            });
                })
                .replaceWithVoid();
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

    Uni<Boolean> syncRuntimeAssignment(final Long runtimeId,
                                   final Long clientId,
                                   final String idempotencyKey) {
        final var runtimeAssignment = runtimeAssignmentModelFactory.create(runtimeId,
                clientId,
                idempotencyKey);
        final var request = new SyncRuntimeAssignmentRequest(runtimeAssignment);
        return runtimeModule.getRuntimeService().syncRuntimeAssignment(request)
                .map(SyncRuntimeAssignmentResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", runtimeAssignment, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}

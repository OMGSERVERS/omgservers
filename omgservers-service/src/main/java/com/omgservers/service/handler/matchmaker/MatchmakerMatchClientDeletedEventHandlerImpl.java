package com.omgservers.service.handler.matchmaker;

import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.internal.LobbyAssignmentRequestedEventBodyModel;
import com.omgservers.schema.event.body.module.matchmaker.MatchmakerMatchClientDeletedEventBodyModel;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchClientResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.service.system.SyncEventRequest;
import com.omgservers.schema.service.system.SyncEventResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.server.service.event.EventService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerMatchClientDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final ClientModule clientModule;
    final EventService eventService;

    final EventModelFactory eventModelFactory;

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

                    return getMatchmaker(matchmakerId)
                            .flatMap(matchmaker -> {
                                if (matchmaker.getDeleted()) {
                                    log.warn("Matchmaker was already deleted, skip lobby assignment, " +
                                            "matchmakerId={}", matchmakerId);
                                    return Uni.createFrom().voidItem();
                                }

                                return getClient(clientId)
                                        .flatMap(client -> {
                                            if (client.getDeleted()) {
                                                log.warn("Client was already deleted, skip lobby assignment, " +
                                                        "clientId={}", clientId);
                                                return Uni.createFrom().voidItem();
                                            }

                                            final var tenantId = client.getTenantId();
                                            final var versionId = client.getVersionId();
                                            final var idempotencyKey = event.getId().toString();

                                            return syncClientRandomLobbyAssignmentRequestedEvent(clientId,
                                                    tenantId,
                                                    versionId,
                                                    idempotencyKey);
                                        });
                            });
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
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

    Uni<Boolean> syncClientRandomLobbyAssignmentRequestedEvent(final Long clientId,
                                                               final Long tenantId,
                                                               final Long versionId,
                                                               final String idempotencyKey) {
        final var eventBody = new LobbyAssignmentRequestedEventBodyModel(clientId,
                tenantId,
                versionId);
        final var eventModel = eventModelFactory.create(eventBody,
                idempotencyKey + "/" + eventBody.getQualifier());

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEvent(syncEventRequest)
                .map(SyncEventResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", eventModel, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}

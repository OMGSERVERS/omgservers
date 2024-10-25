package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerMatchAssignmentResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.LobbyAssignmentRequestedEventBodyModel;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.service.event.EventService;
import com.omgservers.service.service.event.dto.SyncEventRequest;
import com.omgservers.service.service.event.dto.SyncEventResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerMatchAssignmentDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final ClientModule clientModule;
    final EventService eventService;

    final EventModelFactory eventModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_ASSIGNMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerMatchAssignmentDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var id = body.getId();

        return getMatchmakerMatchAssignment(matchmakerId, id)
                .flatMap(matchmakerMatchAssignment -> {
                    log.info("Deleted, {}", matchmakerMatchAssignment);

                    final var clientId = matchmakerMatchAssignment.getClientId();
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
                                            final var deploymentId = client.getDeploymentId();
                                            final var idempotencyKey = event.getId().toString();

                                            return syncClientRandomLobbyAssignmentRequestedEvent(clientId,
                                                    tenantId,
                                                    deploymentId,
                                                    idempotencyKey);
                                        });
                            });
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getService().execute(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<MatchmakerMatchAssignmentModel> getMatchmakerMatchAssignment(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerMatchAssignmentRequest(matchmakerId, id);
        return matchmakerModule.getService().execute(request)
                .map(GetMatchmakerMatchAssignmentResponse::getMatchmakerMatchAssignment);
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientModule.getService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<Boolean> syncClientRandomLobbyAssignmentRequestedEvent(final Long clientId,
                                                               final Long tenantId,
                                                               final Long deploymentId,
                                                               final String idempotencyKey) {
        final var eventBody = new LobbyAssignmentRequestedEventBodyModel(clientId,
                tenantId,
                deploymentId);
        final var eventIdempotencyKey = idempotencyKey + "/" + eventBody.getQualifier();
        final var eventModel = eventModelFactory.create(eventBody, eventIdempotencyKey);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}

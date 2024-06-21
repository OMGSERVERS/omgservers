package com.omgservers.service.handler.matchmaker;

import com.omgservers.model.dto.client.SyncClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.SyncClientMatchmakerRefResponse;
import com.omgservers.model.dto.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerAssignmentResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.matchmaker.MatchmakerAssignmentCreatedEventBodyModel;
import com.omgservers.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.client.ClientMatchmakerRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerAssignmentCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final ClientModule clientModule;

    final ClientMatchmakerRefModelFactory clientMatchmakerRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_ASSIGNMENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerAssignmentCreatedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var id = body.getId();

        return getMatchmakerAssignment(matchmakerId, id)
                .flatMap(matchmakerAssignment -> {
                    final var clientId = matchmakerAssignment.getClientId();

                    log.info("Matchmaker assignment was created, matchmakerAssignment={}/{}, clientId={}",
                            matchmakerId, id, clientId);

                    final var idempotencyKey = event.getIdempotencyKey();
                    return syncClientMatchmakerRef(clientId, matchmakerId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerAssignmentModel> getMatchmakerAssignment(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerAssignmentRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerService().getMatchmakerAssignment(request)
                .map(GetMatchmakerAssignmentResponse::getMatchmakerAssignment);
    }

    Uni<Boolean> syncClientMatchmakerRef(final Long clientId,
                                         final Long matchmakerId,
                                         final String idempotencyKey) {
        final var clientMatchmakerRef = clientMatchmakerRefModelFactory.create(clientId, matchmakerId, idempotencyKey);
        final var request = new SyncClientMatchmakerRefRequest(clientMatchmakerRef);
        return clientModule.getClientService().syncClientMatchmakerRef(request)
                .map(SyncClientMatchmakerRefResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", clientMatchmakerRef, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}

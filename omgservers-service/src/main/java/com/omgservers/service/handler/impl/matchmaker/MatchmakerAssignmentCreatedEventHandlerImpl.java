package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.schema.module.client.SyncClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.SyncClientMatchmakerRefResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerAssignmentCreatedEventBodyModel;
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
                    log.info("Created, {}", matchmakerAssignment);

                    final var clientId = matchmakerAssignment.getClientId();
                    final var idempotencyKey = event.getId().toString();
                    return syncClientMatchmakerRef(clientId, matchmakerId, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerAssignmentModel> getMatchmakerAssignment(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerAssignmentRequest(matchmakerId, id);
        return matchmakerModule.getService().execute(request)
                .map(GetMatchmakerAssignmentResponse::getMatchmakerAssignment);
    }

    Uni<Boolean> syncClientMatchmakerRef(final Long clientId,
                                         final Long matchmakerId,
                                         final String idempotencyKey) {
        final var clientMatchmakerRef = clientMatchmakerRefModelFactory.create(clientId, matchmakerId, idempotencyKey);
        final var request = new SyncClientMatchmakerRefRequest(clientMatchmakerRef);
        return clientModule.getService().syncClientMatchmakerRef(request)
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

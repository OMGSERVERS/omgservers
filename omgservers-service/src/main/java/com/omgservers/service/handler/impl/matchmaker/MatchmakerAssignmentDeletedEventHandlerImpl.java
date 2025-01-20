package com.omgservers.service.handler.impl.matchmaker;

import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.schema.module.client.DeleteClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.DeleteClientMatchmakerRefResponse;
import com.omgservers.schema.module.client.FindClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.FindClientMatchmakerRefResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerAssignmentResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.matchmaker.MatchmakerAssignmentDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.ClientMatchmakerRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerAssignmentDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final ClientShard clientShard;

    final ClientMatchmakerRefModelFactory clientMatchmakerRefModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_ASSIGNMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (MatchmakerAssignmentDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var id = body.getId();

        return getMatchmakerAssignment(matchmakerId, id)
                .flatMap(matchmakerAssignment -> {
                    log.debug("Deleted, {}", matchmakerAssignment);

                    final var clientId = matchmakerAssignment.getClientId();
                    return findAndDeleteClientMatchmakerRef(clientId, matchmakerId);
                })
                .replaceWithVoid();
    }

    Uni<MatchmakerAssignmentModel> getMatchmakerAssignment(final Long matchmakerId, final Long id) {
        final var request = new GetMatchmakerAssignmentRequest(matchmakerId, id);
        return matchmakerShard.getService().execute(request)
                .map(GetMatchmakerAssignmentResponse::getMatchmakerAssignment);
    }

    Uni<Void> findAndDeleteClientMatchmakerRef(final Long clientId, final Long matchmakerId) {
        return findClientMatchmakerRef(clientId, matchmakerId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(clientMatchmakerRef ->
                        deleteClientMatchmakerRef(clientId, clientMatchmakerRef.getId()))
                .replaceWithVoid();
    }

    Uni<ClientMatchmakerRefModel> findClientMatchmakerRef(final Long clientId, final Long matchmakerId) {
        final var request = new FindClientMatchmakerRefRequest(clientId, matchmakerId);
        return clientShard.getService().findClientMatchmakerRef(request)
                .map(FindClientMatchmakerRefResponse::getClientMatchmakerRef);
    }

    Uni<Boolean> deleteClientMatchmakerRef(final Long clientId, final Long id) {
        final var request = new DeleteClientMatchmakerRefRequest(clientId, id);
        return clientShard.getService().deleteClientMatchmakerRef(request)
                .map(DeleteClientMatchmakerRefResponse::getDeleted);
    }
}

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
import com.omgservers.service.event.body.module.matchmaker.MatchmakerMatchAssignmentDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.operation.queue.CreateQueueRequestOperation;
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

    final CreateQueueRequestOperation createQueueRequestOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MATCH_ASSIGNMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (MatchmakerMatchAssignmentDeletedEventBodyModel) event.getBody();
        final var matchmakerId = body.getMatchmakerId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getMatchmakerMatchAssignment(matchmakerId, id)
                .flatMap(matchmakerMatchAssignment -> {
                    log.debug("Deleted, {}", matchmakerMatchAssignment);

                    final var clientId = matchmakerMatchAssignment.getClientId();
                    return getMatchmaker(matchmakerId)
                            .flatMap(matchmaker -> {
                                if (matchmaker.getDeleted()) {
                                    log.warn("The matchmaker \"{}\" was already deleted, skip lobby assignment",
                                            matchmakerId);
                                    return Uni.createFrom().voidItem();
                                }

                                return getClient(clientId)
                                        .flatMap(client -> {
                                            if (client.getDeleted()) {
                                                log.warn("The client \"{}\" was already deleted, skip lobby assignment",
                                                        clientId);
                                                return Uni.createFrom().voidItem();
                                            }

                                            final var tenantId = client.getTenantId();
                                            final var tenantDeploymentId = client.getDeploymentId();
                                            return createQueueRequestOperation.execute(clientId,
                                                    tenantId,
                                                    tenantDeploymentId,
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
}

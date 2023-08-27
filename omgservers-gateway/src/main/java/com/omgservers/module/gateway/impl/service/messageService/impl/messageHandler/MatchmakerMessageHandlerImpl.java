package com.omgservers.module.gateway.impl.service.messageService.impl.messageHandler;

import com.omgservers.dto.internal.FireEventShardedRequest;
import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import com.omgservers.model.event.body.MatchmakerRequestedEventBodyModel;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.module.gateway.impl.service.connectionService.ConnectionHelpService;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetAssignedPlayerHelpRequest;
import com.omgservers.module.gateway.impl.service.messageService.impl.MessageHandler;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerMessageHandlerImpl implements MessageHandler {

    final InternalModule internalModule;

    final ConnectionHelpService connectionInternalService;

    final GetConfigOperation getConfigOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.MATCHMAKER_MESSAGE;
    }

    @Override
    public Uni<Void> handle(Long connectionId, MessageModel message) {
        final var assignedPlayer = getAssignedPlayer(connectionId);
        final var tenantId = assignedPlayer.getTenantId();
        final var stageId = assignedPlayer.getStageId();
        final var userId = assignedPlayer.getUserId();
        final var playerId = assignedPlayer.getPlayerId();
        final var clientId = assignedPlayer.getClientId();
        final var messageBody = (MatchmakerMessageBodyModel) message.getBody();
        final var mode = messageBody.getMode();

        final var eventBody = new MatchmakerRequestedEventBodyModel(tenantId, stageId, userId, playerId, clientId, mode);
        final var event = eventModelFactory.create(eventBody);
        final var request = new FireEventShardedRequest(event);
        return internalModule.getEventShardedService().fireEvent(request)
                .replaceWithVoid();
    }

    AssignedPlayerModel getAssignedPlayer(Long connectionId) {
        final var request = new GetAssignedPlayerHelpRequest(connectionId);
        final var response = connectionInternalService.getAssignedPlayer(request);
        return response.getAssignedPlayer();
    }
}

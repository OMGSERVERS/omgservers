package com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.impl.messageHandler;

import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.ConnectionHelpService;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.GetAssignedPlayerHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.handlerHelpService.impl.MessageHandler;
import com.omgservers.base.InternalModule;
import com.omgservers.base.impl.service.eventHelpService.request.FireEventHelpRequest;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.base.impl.operation.getConfigOperation.GetConfigOperation;
import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import com.omgservers.model.event.body.MatchmakerRequestedEventBodyModel;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
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
    final TenantModule tenantModule;

    final ConnectionHelpService connectionInternalService;

    final GetConfigOperation getConfigOperation;

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
        final var request = new FireEventHelpRequest(eventBody);
        return internalModule.getEventHelpService().fireEvent(request)
                .replaceWithVoid();
    }

    AssignedPlayerModel getAssignedPlayer(Long connectionId) {
        final var request = new GetAssignedPlayerHelpRequest(connectionId);
        final var response = connectionInternalService.getAssignedPlayer(request);
        return response.getAssignedPlayer();
    }
}

package com.omgservers.module.gateway.impl.service.messageService.impl.messageHandler;

import com.omgservers.dto.internal.FireEventRequest;
import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import com.omgservers.model.event.body.MatchMessageReceivedEventBodyModel;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.MatchMessageBodyModel;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetAssignedPlayerRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetAssignedRuntimeRequest;
import com.omgservers.module.gateway.impl.service.messageService.impl.MessageHandler;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.EventModelFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchMessageHandlerImpl implements MessageHandler {

    final GatewayModule gatewayModule;
    final SystemModule systemModule;

    final EventModelFactory eventModelFactory;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.MATCH_MESSAGE;
    }

    @Override
    public Uni<Void> handle(Long connectionId, MessageModel message) {
        final var assignedPlayer = getAssignedPlayer(connectionId);
        final var tenantId = assignedPlayer.getTenantId();
        final var stageId = assignedPlayer.getStageId();
        final var userId = assignedPlayer.getUserId();
        final var playerId = assignedPlayer.getPlayerId();
        final var clientId = assignedPlayer.getClientId();

        final var assignedRuntime = getAssignedRuntime(connectionId);
        final var runtimeId = assignedRuntime.getRuntimeId();

        final var eventBodyBuilder = MatchMessageReceivedEventBodyModel.builder()
                .tenantId(tenantId)
                .stageId(stageId)
                .userId(userId)
                .playerId(playerId)
                .clientId(clientId)
                .runtimeId(runtimeId);

        final var messageBody = (MatchMessageBodyModel) message.getBody();
        final var messageText = messageBody.getText();
        if (Objects.nonNull(messageText)) {
            final var eventBody = eventBodyBuilder.text(messageText).build();
            final var event = eventModelFactory.create(eventBody);
            final var fireEventRequest = new FireEventRequest(event);
            return systemModule.getEventService().fireEvent(fireEventRequest)
                    .replaceWithVoid();
        } else {
            return Uni.createFrom().voidItem();
        }
    }

    AssignedPlayerModel getAssignedPlayer(Long connectionId) {
        final var request = new GetAssignedPlayerRequest(connectionId);
        final var response = gatewayModule.getConnectionService().getAssignedPlayer(request);
        return response.getAssignedPlayer();
    }

    AssignedRuntimeModel getAssignedRuntime(Long connectionId) {
        final var request = new GetAssignedRuntimeRequest(connectionId);
        final var response = gatewayModule.getConnectionService().getAssignedRuntime(request);
        return response.getAssignedRuntime();
    }
}

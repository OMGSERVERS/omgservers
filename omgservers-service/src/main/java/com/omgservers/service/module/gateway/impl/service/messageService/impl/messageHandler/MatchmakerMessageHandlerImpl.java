package com.omgservers.service.module.gateway.impl.service.messageService.impl.messageHandler;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.assignedClient.AssignedClientModel;
import com.omgservers.model.event.body.MatchmakerMessageReceivedEventBodyModel;
import com.omgservers.model.message.MessageModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.service.module.gateway.impl.service.connectionService.ConnectionService;
import com.omgservers.service.module.gateway.impl.service.connectionService.request.GetAssignedClientRequest;
import com.omgservers.service.module.gateway.impl.service.messageService.impl.MessageHandler;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.factory.EventModelFactory;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerMessageHandlerImpl implements MessageHandler {

    final SystemModule systemModule;

    final ConnectionService connectionInternalService;

    final GetConfigOperation getConfigOperation;

    final EventModelFactory eventModelFactory;

    @Override
    public MessageQualifierEnum getQualifier() {
        return MessageQualifierEnum.MATCHMAKER_MESSAGE;
    }

    @Override
    public Uni<Void> handle(Long connectionId, MessageModel message) {
        log.debug("Handle matchmaker message, connectionId={}, message={}", connectionId, message);

        final var assignedClient = getAssignedClient(connectionId);
        final var tenantId = assignedClient.getTenantId();
        final var stageId = assignedClient.getStageId();
        final var userId = assignedClient.getUserId();
        final var playerId = assignedClient.getPlayerId();
        final var clientId = assignedClient.getClientId();
        final var messageBody = (MatchmakerMessageBodyModel) message.getBody();
        final var mode = messageBody.getMode();

        final var eventBody = new MatchmakerMessageReceivedEventBodyModel(tenantId, stageId, userId, playerId, clientId, mode);
        final var event = eventModelFactory.create(eventBody);
        final var request = new SyncEventRequest(event);
        return systemModule.getEventService().syncEvent(request)
                .replaceWithVoid();
    }

    AssignedClientModel getAssignedClient(Long connectionId) {
        final var request = new GetAssignedClientRequest(connectionId);
        final var response = connectionInternalService.getAssignedClient(request);
        return response.getAssignedClient();
    }
}

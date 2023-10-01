package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.respondMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import com.omgservers.module.gateway.impl.operation.sendMessage.SendMessageOperation;
import com.omgservers.module.gateway.impl.service.connectionService.ConnectionService;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetSessionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RespondMessageMethodImpl implements RespondMessageMethod {

    final ConnectionService connectionInternalService;
    final SendMessageOperation sendMessageOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request) {
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> {
                    final var connectionId = request.getConnectionId();
                    final var getSessionHelpRequest = new GetSessionRequest(connectionId);
                    final var session = connectionInternalService.getSession(getSessionHelpRequest).getSession();
                    final var message = request.getMessage();
                    return sendMessageOperation.sendMessage(session, message);
                })
                .replaceWith(new RespondMessageResponse());
    }
}

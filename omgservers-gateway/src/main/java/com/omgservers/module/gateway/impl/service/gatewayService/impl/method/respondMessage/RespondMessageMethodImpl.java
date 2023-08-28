package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.respondMessage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dto.gateway.RespondMessageRoutedRequest;
import com.omgservers.module.gateway.impl.operation.sendMessage.SendMessageOperation;
import com.omgservers.module.gateway.impl.service.connectionService.ConnectionHelpService;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetSessionHelpRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RespondMessageMethodImpl implements RespondMessageMethod {

    final ConnectionHelpService connectionInternalService;
    final SendMessageOperation sendMessageOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> respondMessage(RespondMessageRoutedRequest request) {
        RespondMessageRoutedRequest.validate(request);

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> {
                    final var connectionId = request.getConnectionId();
                    final var getSessionHelpRequest = new GetSessionHelpRequest(connectionId);
                    final var session = connectionInternalService.getSession(getSessionHelpRequest).getSession();
                    final var message = request.getMessage();
                    return sendMessageOperation.sendMessage(session, message);
                });
    }
}

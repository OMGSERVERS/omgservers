package com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.impl.method.respondMessageMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.userModule.impl.operation.sendMessageOperation.SendMessageOperation;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.ConnectionHelpService;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.GetSessionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.request.RespondMessageInternalRequest;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RespondMessageMethodImpl implements RespondMessageMethod {

    final ConnectionHelpService connectionInternalService;
    final SendMessageOperation sendMessageOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> respondMessage(RespondMessageInternalRequest request) {
        RespondMessageInternalRequest.validate(request);

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

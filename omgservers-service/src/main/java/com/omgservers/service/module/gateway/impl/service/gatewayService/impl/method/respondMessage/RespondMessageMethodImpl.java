package com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.respondMessage;

import com.omgservers.model.dto.gateway.RespondMessageRequest;
import com.omgservers.model.dto.gateway.RespondMessageResponse;
import com.omgservers.service.module.gateway.GatewayModule;
import com.omgservers.service.module.gateway.impl.operation.sendMessage.SendMessageOperation;
import com.omgservers.service.module.gateway.impl.service.connectionService.request.GetSessionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RespondMessageMethodImpl implements RespondMessageMethod {

    final GatewayModule gatewayModule;

    final SendMessageOperation sendMessageOperation;

    @Override
    public Uni<RespondMessageResponse> respondMessage(final RespondMessageRequest request) {
        log.debug("Respond message, request={}", request);

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> {
                    final var connectionId = request.getConnectionId();
                    final var getSessionRequest = new GetSessionRequest(connectionId);
                    final var session = gatewayModule.getConnectionService().getSession(getSessionRequest)
                            .getSession();
                    final var message = request.getMessage();
                    return sendMessageOperation.sendMessage(session, message);
                })
                .replaceWith(new RespondMessageResponse());
    }
}

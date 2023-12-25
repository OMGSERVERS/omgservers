package com.omgservers.service.module.gateway.impl.service.gatewayService.impl.method.closeConnection;

import com.omgservers.model.dto.gateway.CloseConnectionRequest;
import com.omgservers.model.dto.gateway.CloseConnectionResponse;
import com.omgservers.service.module.gateway.GatewayModule;
import com.omgservers.service.module.gateway.impl.operation.sendMessage.SendMessageOperation;
import com.omgservers.service.module.gateway.impl.service.connectionService.request.GetSessionRequest;
import com.omgservers.service.module.gateway.impl.service.websocketService.WebsocketService;
import com.omgservers.service.module.gateway.impl.service.websocketService.request.CloseSessionRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CloseConnectionMethodImpl implements CloseConnectionMethod {

    final GatewayModule gatewayModule;

    final WebsocketService websocketService;

    final SendMessageOperation sendMessageOperation;

    @Override
    public Uni<CloseConnectionResponse> closeConnection(final CloseConnectionRequest request) {
        log.debug("Close connection, request={}", request);

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var connectionId = request.getConnectionId();
                    final var getSessionRequest = new GetSessionRequest(connectionId);
                    final var session = gatewayModule.getConnectionService().getSession(getSessionRequest)
                            .getSession();
                    final var reason = request.getReason();
                    websocketService.closeSession(new CloseSessionRequest(session, reason));
                })
                .replaceWith(new CloseConnectionResponse());
    }
}

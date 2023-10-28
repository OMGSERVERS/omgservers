package com.omgservers.module.user.impl.service.userService.impl.method.respondClient;

import com.omgservers.model.dto.gateway.RespondMessageRequest;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.RespondClientRequest;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.client.ClientModel;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RespondClientMethodImpl implements RespondClientMethod {

    final GatewayModule gatewayModule;
    final UserModule userModule;

    @Override
    public Uni<Void> respondClient(RespondClientRequest request) {
        final var userId = request.getUserId();
        final var clientId = request.getClientId();

        return getClient(userId, clientId)
                .onFailure(ServerSideNotFoundException.class).recoverWithNull()
                .invoke(client -> {
                    if (Objects.isNull(client)) {
                        log.warn("Respond client method failed, client doesn't exist anymore, " +
                                        "userId={}, " +
                                        "clientId={}, " +
                                        "messageQualifier={}",
                                userId,
                                clientId,
                                request.getMessage().getQualifier());
                    }
                })
                .onItem().ifNotNull().transformToUni(client -> {
                    final var server = client.getServer();
                    final var connection = client.getConnectionId();
                    final var message = request.getMessage();
                    final var respondMessageRequest = new RespondMessageRequest(server, connection, message);
                    return gatewayModule.getGatewayService().respondMessage(respondMessageRequest);
                })
                .replaceWithVoid();
    }

    Uni<ClientModel> getClient(Long userId, Long clientId) {
        final var getClientServiceRequest = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(getClientServiceRequest)
                .map(GetClientResponse::getClient);
    }
}

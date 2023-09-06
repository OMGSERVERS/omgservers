package com.omgservers.module.user.impl.service.userService.impl.method.respondClient;

import com.omgservers.dto.gateway.RespondMessageRoutedRequest;
import com.omgservers.dto.user.GetClientShardedRequest;
import com.omgservers.dto.user.GetClientShardedResponse;
import com.omgservers.model.client.ClientModel;
import com.omgservers.module.gateway.GatewayModule;
import com.omgservers.module.user.UserModule;
import com.omgservers.dto.user.RespondClientRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class RespondClientMethodImpl implements RespondClientMethod {

    final GatewayModule gatewayModule;
    final UserModule userModule;

    @Override
    public Uni<Void> respondClient(RespondClientRequest request) {
        RespondClientRequest.validateRespondClientServiceRequest(request);

        final var userId = request.getUserId();
        final var clientId = request.getClientId();

        return getClient(userId, clientId)
                .flatMap(client -> {
                    final var server = client.getServer();
                    final var connection = client.getConnectionId();
                    final var message = request.getMessage();
                    final var respondMessageInternalRequest =
                            new RespondMessageRoutedRequest(server, connection, message);
                    return gatewayModule.getGatewayService().respondMessage(respondMessageInternalRequest);
                });
    }

    Uni<ClientModel> getClient(Long userId, Long clientId) {
        final var getClientServiceRequest = new GetClientShardedRequest(userId, clientId);
        return userModule.getClientShardedService().getClient(getClientServiceRequest)
                .map(GetClientShardedResponse::getClient);
    }
}

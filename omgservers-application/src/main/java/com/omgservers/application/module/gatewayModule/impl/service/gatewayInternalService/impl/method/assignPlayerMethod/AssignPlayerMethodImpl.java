package com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.impl.method.assignPlayerMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.userModule.impl.operation.sendMessageOperation.SendMessageOperation;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.ConnectionHelpService;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.AssignPlayerHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.request.AssignPlayerInternalRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AssignPlayerMethodImpl implements AssignPlayerMethod {

    final ConnectionHelpService connectionInternalService;
    final SendMessageOperation sendMessageOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> assignPlayer(AssignPlayerInternalRequest request) {
        AssignPlayerInternalRequest.validate(request);

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var connection = request.getConnection();
                    final var assignedPlayer = request.getAssignedPlayer();
                    final var assignPlayerInternalRequest = new AssignPlayerHelpRequest(connection, assignedPlayer);
                    connectionInternalService.assignPlayer(assignPlayerInternalRequest);
                });
    }
}

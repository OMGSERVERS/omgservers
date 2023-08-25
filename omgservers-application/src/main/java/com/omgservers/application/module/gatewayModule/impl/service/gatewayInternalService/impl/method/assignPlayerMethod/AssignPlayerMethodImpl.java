package com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.impl.method.assignPlayerMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.ConnectionHelpService;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.AssignPlayerHelpRequest;
import com.omgservers.base.InternalModule;
import com.omgservers.base.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.userModule.impl.operation.sendMessageOperation.SendMessageOperation;
import com.omgservers.dto.gatewayModule.AssignPlayerInternalRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AssignPlayerMethodImpl implements AssignPlayerMethod {

    final InternalModule internalModule;

    final ConnectionHelpService connectionInternalService;

    final SendMessageOperation sendMessageOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> assignPlayer(AssignPlayerInternalRequest request) {
        AssignPlayerInternalRequest.validate(request);

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var connectionId = request.getConnectionId();
                    final var assignedPlayer = request.getAssignedPlayer();
                    final var assignPlayerInternalRequest = new AssignPlayerHelpRequest(connectionId, assignedPlayer);
                    connectionInternalService.assignPlayer(assignPlayerInternalRequest);
                })
                .call(voidItem -> {
                    final var syncLog = logModelFactory.create("Player was assigned, request=" + request);
                    final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                    return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                });
    }
}

package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.dto.gateway.AssignPlayerRoutedRequest;
import com.omgservers.dto.internal.SyncLogRequest;
import com.omgservers.module.gateway.impl.service.connectionService.ConnectionHelpService;
import com.omgservers.module.gateway.impl.service.connectionService.request.AssignPlayerHelpRequest;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.factory.LogModelFactory;
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

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> assignPlayer(AssignPlayerRoutedRequest request) {
        AssignPlayerRoutedRequest.validate(request);

        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var connectionId = request.getConnectionId();
                    final var assignedPlayer = request.getAssignedPlayer();
                    final var assignPlayerInternalRequest = new AssignPlayerHelpRequest(connectionId, assignedPlayer);
                    connectionInternalService.assignPlayer(assignPlayerInternalRequest);
                })
                .call(voidItem -> {
                    final var syncLog = logModelFactory.create("Player was assigned, request=" + request);
                    final var syncLogHelpRequest = new SyncLogRequest(syncLog);
                    return internalModule.getLogService().syncLog(syncLogHelpRequest);
                });
    }
}

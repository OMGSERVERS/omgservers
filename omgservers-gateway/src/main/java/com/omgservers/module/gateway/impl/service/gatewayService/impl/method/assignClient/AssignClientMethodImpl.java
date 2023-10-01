package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignClient;

import com.omgservers.dto.gateway.AssignClientRequest;
import com.omgservers.dto.internal.SyncLogRequest;
import com.omgservers.module.gateway.impl.service.connectionService.ConnectionService;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.factory.LogModelFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AssignClientMethodImpl implements AssignClientMethod {

    final SystemModule systemModule;

    final ConnectionService connectionService;

    final LogModelFactory logModelFactory;

    @Override
    public Uni<Void> assignClient(AssignClientRequest request) {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var connectionId = request.getConnectionId();
                    final var assignedClient = request.getAssignedClient();
                    final var assignClientRequest =
                            new com.omgservers.module.gateway.impl.service.connectionService.request.AssignClientRequest(
                                    connectionId, assignedClient);
                    connectionService.assignClient(assignClientRequest);
                })
                .call(voidItem -> {
                    final var syncLog = logModelFactory.create("Player was assigned, request=" + request);
                    final var syncLogRequest = new SyncLogRequest(syncLog);
                    return systemModule.getLogService().syncLog(syncLogRequest);
                });
    }
}

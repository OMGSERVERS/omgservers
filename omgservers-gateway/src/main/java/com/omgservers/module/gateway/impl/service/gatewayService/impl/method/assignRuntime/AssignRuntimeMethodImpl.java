package com.omgservers.module.gateway.impl.service.gatewayService.impl.method.assignRuntime;

import com.omgservers.dto.gateway.AssignRuntimeRequest;
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
class AssignRuntimeMethodImpl implements AssignRuntimeMethod {

    final SystemModule systemModule;

    final ConnectionService connectionService;

    final LogModelFactory logModelFactory;

    @Override
    public Uni<Void> assignRuntime(AssignRuntimeRequest request) {
        return Uni.createFrom().voidItem()
                .invoke(voidItem -> {
                    final var connectionId = request.getConnectionId();
                    final var assignedRuntime = request.getAssignedRuntime();
                    final var assignRuntimeRequest =
                            new com.omgservers.module.gateway.impl.service.connectionService.request.AssignRuntimeRequest(
                                    connectionId, assignedRuntime);
                    connectionService.assignRuntime(assignRuntimeRequest);
                })
                .call(voidItem -> {
                    final var syncLog = logModelFactory.create("Runtime was assigned, request=" + request);
                    final var syncLogRequest = new SyncLogRequest(syncLog);
                    return systemModule.getLogService().syncLog(syncLogRequest);
                });
    }
}

package com.omgservers.service.module.worker.impl.service.workerService.impl.method.handleRuntimeCommands;

import com.omgservers.model.dto.runtime.HandleRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.HandleRuntimeCommandsResponse;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.HandleRuntimeCommandsWorkerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.checkRuntimePermission.CheckRuntimePermissionOperation;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.mapDoCommand.MapDoCommandOperation;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleRuntimeCommandsMethodImpl implements HandleRuntimeCommandsMethod {

    final RuntimeModule runtimeModule;

    final CheckRuntimePermissionOperation checkRuntimePermissionOperation;
    final MapDoCommandOperation mapDoCommandOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<HandleRuntimeCommandsWorkerResponse> handleRuntimeCommands(
            final HandleRuntimeCommandsWorkerRequest request) {
        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var runtimeId = request.getRuntimeId();

        return checkRuntimePermissionOperation.checkRuntimePermission(runtimeId,
                        userId,
                        RuntimePermissionEnum.HANDLE_RUNTIME)
                .flatMap(runtimePermission -> {
                    final var runtimeCommandIds = request.getRuntimeCommandIds();
                    final var events = request.getDoCommands().stream()
                            .map(doCommand -> mapDoCommandOperation.mapDoCommand(runtimeId, doCommand))
                            .toList();
                    return handleRuntimeCommands(runtimeId, runtimeCommandIds, events);
                })
                .map(HandleRuntimeCommandsWorkerResponse::new);
    }

    Uni<Boolean> handleRuntimeCommands(final Long runtimeId,
                                       final List<Long> runtimeCommandIds,
                                       final List<EventModel> events) {
        final var request = new HandleRuntimeCommandsRequest(runtimeId, runtimeCommandIds, events);
        return runtimeModule.getRuntimeService().handleRuntimeCommands(request)
                .map(HandleRuntimeCommandsResponse::getHandled);
    }
}

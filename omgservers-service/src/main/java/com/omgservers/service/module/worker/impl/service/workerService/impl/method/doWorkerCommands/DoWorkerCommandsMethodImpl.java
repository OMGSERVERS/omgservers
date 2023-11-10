package com.omgservers.service.module.worker.impl.service.workerService.impl.method.doWorkerCommands;

import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerRequest;
import com.omgservers.model.dto.worker.DoWorkerCommandsWorkerResponse;
import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.checkRuntimePermission.CheckRuntimePermissionOperation;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.executeDoCommand.ExecuteDoCommandOperation;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DoWorkerCommandsMethodImpl implements DoWorkerCommandsMethod {

    final RuntimeModule runtimeModule;

    final CheckRuntimePermissionOperation checkRuntimePermissionOperation;
    final ExecuteDoCommandOperation executeDoCommandOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DoWorkerCommandsWorkerResponse> doWorkerCommands(final DoWorkerCommandsWorkerRequest request) {
        final var runtimeId = request.getRuntimeId();
        final var userId = securityIdentity.<Long>getAttribute("userId");

        return checkRuntimePermissionOperation.checkRuntimePermission(runtimeId,
                        userId,
                        RuntimePermissionEnum.HANDLE_RUNTIME)
                .flatMap(voidItem -> {
                    final var doCommands = request.getDoCommands();
                    return Multi.createFrom().iterable(doCommands)
                            .onItem().transformToUniAndConcatenate(
                                    doCommand -> executeDoCommandOperation.executeDoCommand(runtimeId, doCommand)
                                            .onFailure()
                                            .recoverWithItem(t -> {
                                                log.warn("Do command failed, " +
                                                                "runtimeId={}, " +
                                                                "qualifier={}, " +
                                                                "{}:{}",
                                                        runtimeId,
                                                        doCommand.getQualifier(),
                                                        t.getClass().getSimpleName(),
                                                        t.getMessage());
                                                return null;
                                            }))
                            .collect().asList()
                            .replaceWith(true);
                })
                .map(DoWorkerCommandsWorkerResponse::new);
    }
}

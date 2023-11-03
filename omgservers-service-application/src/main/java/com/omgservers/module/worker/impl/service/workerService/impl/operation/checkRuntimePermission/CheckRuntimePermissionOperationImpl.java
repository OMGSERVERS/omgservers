package com.omgservers.module.worker.impl.service.workerService.impl.operation.checkRuntimePermission;

import com.omgservers.exception.ServerSideForbiddenException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CheckRuntimePermissionOperationImpl implements CheckRuntimePermissionOperation {

    final RuntimeModule runtimeModule;

    @Override
    public Uni<Void> checkRuntimePermission(final Long runtimeId,
                                            final Long userId) {
        final var permission = RuntimePermissionEnum.HANDLE_RUNTIME;
        final var request = new FindRuntimePermissionRequest(runtimeId, userId, permission, false);
        return runtimeModule.getRuntimeService().findRuntimePermission(request)
                .replaceWithVoid()
                .onFailure(ServerSideNotFoundException.class)
                .transform(t -> new ServerSideForbiddenException(String.format("lack of permission, " +
                        "runtimeId=%d, userId=%d, permission=%s", runtimeId, userId, permission)));
    }
}

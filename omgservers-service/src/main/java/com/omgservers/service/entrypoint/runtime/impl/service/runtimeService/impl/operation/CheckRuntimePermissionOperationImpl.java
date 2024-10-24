package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.operation;

import com.omgservers.schema.module.runtime.FindRuntimePermissionRequest;
import com.omgservers.schema.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.runtime.RuntimeModule;
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
                                            final Long userId,
                                            final RuntimePermissionEnum permission) {
        final var request = new FindRuntimePermissionRequest(runtimeId, userId, permission);
        return runtimeModule.getService().execute(request)
                .replaceWithVoid()
                .onFailure(ServerSideNotFoundException.class)
                .transform(t -> new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                        String.format("permission was not found, runtimeId=%d, userId=%d, permission=%s",
                                runtimeId, userId, permission)));
    }
}

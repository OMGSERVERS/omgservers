package com.omgservers.service.module.worker.impl.service.workerService.impl.method.viewRuntimeCommands;

import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerRequest;
import com.omgservers.model.dto.worker.ViewRuntimeCommandsWorkerResponse;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.service.module.runtime.RuntimeModule;
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
class ViewRuntimeCommandsMethodImpl implements ViewRuntimeCommandsMethod {

    final RuntimeModule runtimeModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<ViewRuntimeCommandsWorkerResponse> viewRuntimeCommands(final ViewRuntimeCommandsWorkerRequest request) {
        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var runtimeId = request.getRuntimeId();

        return findRuntimePermission(runtimeId, userId)
                .flatMap(runtimePermission -> viewRuntimeCommands(runtimeId))
                .map(ViewRuntimeCommandsWorkerResponse::new);
    }

    Uni<RuntimePermissionModel> findRuntimePermission(final Long runtimeId,
                                                      final Long userId) {
        final var permission = RuntimePermissionEnum.HANDLE_RUNTIME;
        final var request = new FindRuntimePermissionRequest(runtimeId, userId, permission, false);
        return runtimeModule.getRuntimeService().findRuntimePermission(request)
                .map(FindRuntimePermissionResponse::getRuntimePermission)
                .onFailure(ServerSideNotFoundException.class)
                .transform(t -> new ServerSideForbiddenException(String.format("lack of permission, " +
                        "runtimeId=%d, userId=%d, permission=%s", runtimeId, userId, permission)));
    }

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final Long runtimeId) {
        final var request = new ViewRuntimeCommandsRequest(runtimeId, false);
        return runtimeModule.getRuntimeService().viewRuntimeCommands(request)
                .map(ViewRuntimeCommandsResponse::getRuntimeCommands);
    }
}

package com.omgservers.service.module.worker.impl.service.workerService.impl.method.updateRuntimeState;

import com.omgservers.model.dto.worker.UpdateRuntimeStateWorkerRequest;
import com.omgservers.model.dto.worker.UpdateRuntimeStateWorkerResponse;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.checkRuntimePermission.CheckRuntimePermissionOperation;
import com.omgservers.service.module.worker.impl.service.workerService.impl.operation.mapDoCommand.MapDoCommandOperation;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpdateRuntimeStateMethodImpl implements UpdateRuntimeStateMethod {

    final RuntimeModule runtimeModule;

    final CheckRuntimePermissionOperation checkRuntimePermissionOperation;
    final MapDoCommandOperation mapDoCommandOperation;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<UpdateRuntimeStateWorkerResponse> updateRuntimeState(final UpdateRuntimeStateWorkerRequest request) {
        return null;
    }
}

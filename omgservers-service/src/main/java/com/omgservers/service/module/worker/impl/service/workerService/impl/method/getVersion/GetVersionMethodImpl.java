package com.omgservers.service.module.worker.impl.service.workerService.impl.method.getVersion;

import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetVersionMethodImpl implements GetVersionMethod {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<GetVersionWorkerResponse> getVersion(GetVersionWorkerRequest request) {
        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var runtimeId = request.getRuntimeId();

        return findRuntimePermission(runtimeId, userId)
                .flatMap(runtimePermission -> getRuntime(runtimeId))
                .flatMap(runtime -> {
                    final var tenantId = runtime.getTenantId();
                    final var versionId = runtime.getVersionId();
                    return getVersion(tenantId, versionId);
                })
                .map(GetVersionWorkerResponse::new);
    }

    Uni<RuntimePermissionModel> findRuntimePermission(final Long runtimeId,
                                                      final Long userId) {
        final var permission = RuntimePermissionEnum.HANDLE_RUNTIME;
        final var request = new FindRuntimePermissionRequest(runtimeId, userId, permission);
        return runtimeModule.getRuntimeService().findRuntimePermission(request)
                .map(FindRuntimePermissionResponse::getRuntimePermission)
                .onFailure(ServerSideNotFoundException.class)
                .transform(t -> new ServerSideForbiddenException(String.format("lack of permission, " +
                        "runtimeId=%d, userId=%d, permission=%s", runtimeId, userId, permission)));
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<VersionModel> getVersion(Long tenantId, Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }
}

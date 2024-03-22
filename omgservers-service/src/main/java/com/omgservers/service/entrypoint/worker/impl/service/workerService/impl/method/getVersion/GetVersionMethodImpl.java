package com.omgservers.service.entrypoint.worker.impl.service.workerService.impl.method.getVersion;

import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.worker.GetVersionWorkerRequest;
import com.omgservers.model.dto.worker.GetVersionWorkerResponse;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
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
    public Uni<GetVersionWorkerResponse> getVersion(final GetVersionWorkerRequest request) {
        log.debug("Get version, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var runtimeId = request.getRuntimeId();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    if (runtime.getUserId().equals(userId)) {
                        final var tenantId = runtime.getTenantId();
                        final var versionId = runtime.getVersionId();
                        return getVersion(tenantId, versionId);
                    } else {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.RUNTIME_ID_WRONG,
                                "wrong runtimeId, runtimeId=" + runtimeId);
                    }
                })
                .map(GetVersionWorkerResponse::new);
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

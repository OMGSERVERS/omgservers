package com.omgservers.service.entrypoint.worker.impl.service.workerService.impl.method.getConfig;

import com.omgservers.schema.entrypoint.worker.GetConfigWorkerRequest;
import com.omgservers.schema.entrypoint.worker.GetConfigWorkerResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetConfigMethodImpl implements GetConfigMethod {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    final JsonWebToken jwt;

    @Override
    public Uni<GetConfigWorkerResponse> getConfig(final GetConfigWorkerRequest request) {
        log.debug("Get config, request={}", request);

        final var userId = Long.valueOf(jwt.getClaim(Claims.sub));

        final var runtimeId = request.getRuntimeId();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    if (runtime.getUserId().equals(userId)) {
                        final var tenantId = runtime.getTenantId();
                        final var versionId = runtime.getVersionId();
                        return getVersion(tenantId, versionId)
                                .map(VersionModel::getConfig);
                    } else {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_RUNTIME_ID,
                                "wrong runtimeId, runtimeId=" + runtimeId);
                    }
                })
                .map(GetConfigWorkerResponse::new);
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

package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.selectVersionRuntime;

import com.omgservers.model.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesResponse;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionRuntimeMethodImpl implements SelectVersionRuntimeMethod {

    final TenantModule tenantModule;

    @Override
    public Uni<SelectVersionRuntimeResponse> selectVersionRuntime(final SelectVersionRuntimeRequest request) {
        final var tenantId = request.getTenantId();
        final var versionId = request.getVersionId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> switch (request.getStrategy()) {
                    case RANDOM -> selectRandomVersionRuntime(tenantId, versionId);
                })
                .map(SelectVersionRuntimeResponse::new);
    }

    Uni<VersionRuntimeModel> selectRandomVersionRuntime(final Long tenantId, final Long versionId) {
        return viewVersionRuntimes(tenantId, versionId)
                .map(versionRuntimes -> {
                    if (versionRuntimes.isEmpty()) {
                        throw new ServerSideConflictException("Version runtime were not selected, " +
                                "versionId=" + versionId);
                    } else {
                        final var randomIndex = (new Random()).nextInt(versionRuntimes.size());
                        return versionRuntimes.get(randomIndex);
                    }
                });
    }

    Uni<List<VersionRuntimeModel>> viewVersionRuntimes(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionRuntimesRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionRuntimes(request)
                .map(ViewVersionRuntimesResponse::getVersionRuntimes);
    }
}

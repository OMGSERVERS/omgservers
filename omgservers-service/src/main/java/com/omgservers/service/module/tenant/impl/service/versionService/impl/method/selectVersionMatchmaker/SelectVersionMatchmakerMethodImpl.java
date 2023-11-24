package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.selectVersionMatchmaker;

import com.omgservers.model.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
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
class SelectVersionMatchmakerMethodImpl implements SelectVersionMatchmakerMethod {

    final TenantModule tenantModule;

    @Override
    public Uni<SelectVersionMatchmakerResponse> selectVersionMatchmaker(final SelectVersionMatchmakerRequest request) {
        log.debug("Select version matchmaker, request={}", request);

        final var tenantId = request.getTenantId();
        final var versionId = request.getVersionId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> switch (request.getStrategy()) {
                    case RANDOM -> selectRandomVersionMatchmaker(tenantId, versionId);
                })
                .map(SelectVersionMatchmakerResponse::new);
    }

    Uni<VersionMatchmakerModel> selectRandomVersionMatchmaker(final Long tenantId, final Long versionId) {
        return viewVersionMatchmaker(tenantId, versionId)
                .map(versionMatchmakers -> {
                    if (versionMatchmakers.isEmpty()) {
                        throw new ServerSideConflictException("Version matchmakers were not selected, " +
                                "versionId=" + versionId);
                    } else {
                        final var randomIndex = (new Random()).nextInt(versionMatchmakers.size());
                        return versionMatchmakers.get(randomIndex);
                    }
                });
    }

    Uni<List<VersionMatchmakerModel>> viewVersionMatchmaker(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionMatchmakersRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionMatchmakers(request)
                .map(ViewVersionMatchmakersResponse::getVersionMatchmakers);
    }
}

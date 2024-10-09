package com.omgservers.service.service.task.impl.method.executeStageTask;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsResponse;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StageTaskImpl {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    public Uni<Boolean> executeTask(final Long tenantId, final Long tenantStageId) {
        return getTenantStage(tenantId, tenantStageId)
                .flatMap(stage -> handleStage(stage)
                        .replaceWith(Boolean.TRUE));
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Void> handleStage(final TenantStageModel tenantStage) {
        final var tenantId = tenantStage.getTenantId();
        final var tenantStageId = tenantStage.getId();

        return viewVersionProjections(tenantId, tenantStageId)
                .flatMap(versions -> {
                    if (versions.size() > 1) {
                        final var previousVersions = versions.subList(0, versions.size() - 1);
                        return Multi.createFrom().iterable(previousVersions)
                                .onItem().transformToUniAndConcatenate(this::handlePreviousVersion)
                                .collect().asList()
                                .replaceWithVoid();
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<List<TenantVersionProjectionModel>> viewVersionProjections(final Long tenantId, final Long tenantStageId) {
        final var request = new ViewTenantVersionsRequest(tenantId, tenantStageId);
        return tenantModule.getService().viewTenantVersions(request)
                .map(ViewTenantVersionsResponse::getTenantVersionProjections);
    }

    Uni<Boolean> handlePreviousVersion(final TenantVersionProjectionModel tenantVersionProjection) {
        final var tenantId = tenantVersionProjection.getTenantId();
        final var tenantVersionId = tenantVersionProjection.getId();

        return handlePreviousVersionRuntimes(tenantVersionProjection)
                .flatMap(isEmpty -> {
                    if (isEmpty) {
                        log.info("Previous tenantVersionProjection without clients was found, version={}/{}", tenantId,
                                tenantVersionId);
                        return deleteTenantVersion(tenantId, tenantVersionId);
                    } else {
                        return Uni.createFrom().item(Boolean.FALSE);
                    }
                });
    }

    Uni<Boolean> handlePreviousVersionRuntimes(final TenantVersionProjectionModel tenantVersionProjection) {
        final var tenantId = tenantVersionProjection.getTenantId();
        final var tenantVersionId = tenantVersionProjection.getId();
        return viewTenantLobbyRefs(tenantId, tenantVersionId)
                .flatMap(versionRuntimes -> Multi.createFrom().iterable(versionRuntimes)
                        .onItem().transformToUniAndConcatenate(versionRuntime -> {
                            final var runtimeId = versionRuntime.getLobbyId();
                            return countRuntimeAssignments(runtimeId)
                                    .map(count -> count > 0);
                        })
                        .collect().asList()
                        .map(result -> {
                            // Checking that all runtimes are without clients
                            return result.stream().allMatch(r -> r.equals(Boolean.FALSE));
                        }));
    }

    Uni<List<TenantLobbyRefModel>> viewTenantLobbyRefs(final Long tenantId, final Long tenantVersionId) {
        final var request = new ViewTenantLobbyRefsRequest(tenantId, tenantVersionId);
        return tenantModule.getService().viewTenantLobbyRefs(request)
                .map(ViewTenantLobbyRefsResponse::getTenantLobbyRefs);
    }

    Uni<Integer> countRuntimeAssignments(Long runtimeId) {
        final var request = new CountRuntimeAssignmentsRequest(runtimeId);
        return runtimeModule.getService().countRuntimeAssignments(request)
                .map(CountRuntimeAssignmentsResponse::getCount);
    }

    Uni<Boolean> deleteTenantVersion(final Long tenantId, final Long id) {
        final var request = new DeleteTenantVersionRequest(tenantId, id);
        return tenantModule.getService().deleteTenantVersion(request)
                .map(DeleteTenantVersionResponse::getDeleted);
    }
}

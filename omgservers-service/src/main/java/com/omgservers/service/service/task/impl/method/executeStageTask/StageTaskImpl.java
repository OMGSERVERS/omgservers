package com.omgservers.service.service.task.impl.method.executeStageTask;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsResponse;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.DeleteTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
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

    public Uni<Boolean> executeTask(final Long tenantId, final Long stageId) {
        return getStage(tenantId, stageId)
                .flatMap(stage -> handleStage(stage)
                        .replaceWith(Boolean.TRUE));
    }

    Uni<TenantStageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getTenantService().getStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Void> handleStage(final TenantStageModel stage) {
        final var tenantId = stage.getTenantId();
        final var stageId = stage.getId();

        return viewVersionProjections(tenantId, stageId)
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

    Uni<List<TenantVersionProjectionModel>> viewVersionProjections(final Long tenantId, final Long stageId) {
        final var request = new ViewTenantVersionsRequest(tenantId, stageId);
        return tenantModule.getTenantService().viewTenantVersions(request)
                .map(ViewTenantVersionsResponse::getTenantVersionProjections);
    }

    Uni<Boolean> handlePreviousVersion(final TenantVersionProjectionModel versionProjection) {
        final var tenantId = versionProjection.getTenantId();
        final var versionId = versionProjection.getId();

        return handlePreviousVersionRuntimes(versionProjection)
                .flatMap(isEmpty -> {
                    if (isEmpty) {
                        log.info("Previous versionProjection without clients was found, version={}/{}", tenantId,
                                versionId);
                        return deleteVersion(tenantId, versionId);
                    } else {
                        return Uni.createFrom().item(Boolean.FALSE);
                    }
                });
    }

    Uni<Boolean> handlePreviousVersionRuntimes(final TenantVersionProjectionModel versionProjection) {
        final var tenantId = versionProjection.getTenantId();
        final var versionId = versionProjection.getId();
        return viewVersionLobbyRefs(tenantId, versionId)
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

    Uni<List<TenantLobbyRefModel>> viewVersionLobbyRefs(final Long tenantId, final Long versionId) {
        final var request = new ViewTenantLobbyRefsRequest(tenantId, versionId);
        return tenantModule.getTenantService().viewVersionLobbyRefs(request)
                .map(ViewTenantLobbyRefsResponse::getTenantLobbyRefs);
    }

    Uni<Integer> countRuntimeAssignments(Long runtimeId) {
        final var request = new CountRuntimeAssignmentsRequest(runtimeId);
        return runtimeModule.getRuntimeService().countRuntimeAssignments(request)
                .map(CountRuntimeAssignmentsResponse::getCount);
    }

    Uni<Boolean> deleteVersion(final Long tenantId, final Long id) {
        final var request = new DeleteTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().deleteVersion(request)
                .map(DeleteTenantVersionResponse::getDeleted);
    }
}

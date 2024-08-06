package com.omgservers.service.server.service.task.impl.method.executeStageTask;

import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.model.version.VersionProjectionModel;
import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsResponse;
import com.omgservers.schema.module.tenant.DeleteVersionRequest;
import com.omgservers.schema.module.tenant.DeleteVersionResponse;
import com.omgservers.schema.module.tenant.GetStageRequest;
import com.omgservers.schema.module.tenant.GetStageResponse;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsResponse;
import com.omgservers.schema.module.tenant.ViewVersionsRequest;
import com.omgservers.schema.module.tenant.ViewVersionsResponse;
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

    Uni<StageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetStageRequest(tenantId, id);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }

    Uni<Void> handleStage(final StageModel stage) {
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

    Uni<List<VersionProjectionModel>> viewVersionProjections(final Long tenantId, final Long stageId) {
        final var request = new ViewVersionsRequest(tenantId, stageId);
        return tenantModule.getVersionService().viewVersions(request)
                .map(ViewVersionsResponse::getVersionProjections);
    }

    Uni<Boolean> handlePreviousVersion(final VersionProjectionModel versionProjection) {
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

    Uni<Boolean> handlePreviousVersionRuntimes(final VersionProjectionModel versionProjection) {
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

    Uni<List<VersionLobbyRefModel>> viewVersionLobbyRefs(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionLobbyRefsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionLobbyRefs(request)
                .map(ViewVersionLobbyRefsResponse::getVersionLobbyRefs);
    }

    Uni<Integer> countRuntimeAssignments(Long runtimeId) {
        final var request = new CountRuntimeAssignmentsRequest(runtimeId);
        return runtimeModule.getRuntimeService().countRuntimeAssignments(request)
                .map(CountRuntimeAssignmentsResponse::getCount);
    }

    Uni<Boolean> deleteVersion(final Long tenantId, final Long id) {
        final var request = new DeleteVersionRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersion(request)
                .map(DeleteVersionResponse::getDeleted);
    }
}

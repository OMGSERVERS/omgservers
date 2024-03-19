package com.omgservers.service.handler.job.stage;

import com.omgservers.model.dto.runtime.CountRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.CountRuntimeClientsResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionLobbyRefsResponse;
import com.omgservers.model.dto.tenant.ViewVersionsRequest;
import com.omgservers.model.dto.tenant.ViewVersionsResponse;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
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
public class StageJobTaskImpl {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    public Uni<Boolean> executeTask(final Long tenantId, final Long stageId) {
        return getStage(tenantId, stageId)
                .flatMap(stage -> {
                    if (stage.getDeleted()) {
                        log.info("Stage was deleted, cancel job execution, stage={}/{}", tenantId, stageId);
                        return Uni.createFrom().item(Boolean.FALSE);
                    } else {
                        return handleStage(stage)
                                .replaceWith(Boolean.TRUE);
                    }
                });
    }

    Uni<StageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetStageRequest(tenantId, id);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }

    Uni<Void> handleStage(final StageModel stage) {
        final var tenantId = stage.getTenantId();
        final var stageId = stage.getId();

        return viewVersions(tenantId, stageId)
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

    Uni<List<VersionModel>> viewVersions(final Long tenantId, final Long stageId) {
        final var request = new ViewVersionsRequest(tenantId, stageId);
        return tenantModule.getVersionService().viewVersions(request)
                .map(ViewVersionsResponse::getVersions);
    }

    Uni<Boolean> handlePreviousVersion(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();

        return handlePreviousVersionRuntimes(version)
                .flatMap(isEmpty -> {
                    if (isEmpty) {
                        log.info("Previous version without clients was found, version={}/{}", tenantId, versionId);
                        return deleteVersion(tenantId, versionId);
                    } else {
                        return Uni.createFrom().item(Boolean.FALSE);
                    }
                });
    }

    Uni<Boolean> handlePreviousVersionRuntimes(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        return viewVersionLobbyRefs(tenantId, versionId)
                .flatMap(versionRuntimes -> Multi.createFrom().iterable(versionRuntimes)
                        .onItem().transformToUniAndConcatenate(versionRuntime -> {
                            final var runtimeId = versionRuntime.getLobbyId();
                            return countRuntimeClients(runtimeId)
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

    Uni<Integer> countRuntimeClients(Long runtimeId) {
        final var request = new CountRuntimeClientsRequest(runtimeId);
        return runtimeModule.getRuntimeService().countRuntimeClients(request)
                .map(CountRuntimeClientsResponse::getCount);
    }

    Uni<Boolean> deleteVersion(final Long tenantId, final Long id) {
        final var request = new DeleteVersionRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersion(request)
                .map(DeleteVersionResponse::getDeleted);
    }
}

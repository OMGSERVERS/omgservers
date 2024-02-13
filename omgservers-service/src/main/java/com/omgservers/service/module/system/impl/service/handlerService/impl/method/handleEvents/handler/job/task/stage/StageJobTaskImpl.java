package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.job.task.stage;

import com.omgservers.model.stage.StageModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StageJobTaskImpl {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    public Uni<Boolean> executeTask(final Long tenantId, final Long stageId) {
        return tenantModule.getShortcutService().getStage(tenantId, stageId)
                .flatMap(stage -> {
                    if (stage.getDeleted()) {
                        log.info("Stage was deleted, cancel job execution, stage={}/{}", tenantId, stageId);
                        return Uni.createFrom().item(false);
                    } else {
                        return handleStage(stage)
                                .replaceWith(true);
                    }
                });
    }

    Uni<Void> handleStage(final StageModel stage) {
        final var tenantId = stage.getTenantId();
        final var stageId = stage.getId();

        return tenantModule.getShortcutService().viewVersions(tenantId, stageId)
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

    Uni<Boolean> handlePreviousVersion(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();

        return handlePreviousVersionRuntimes(version)
                .flatMap(areVersionRuntimesEmpty -> {
                    if (areVersionRuntimesEmpty) {
                        log.info("Version without clients was found, version={}/{}", tenantId, versionId);
                        return tenantModule.getShortcutService().deleteVersion(tenantId, versionId);
                    } else {
                        return Uni.createFrom().item(Boolean.FALSE);
                    }
                });
    }

    Uni<Boolean> handlePreviousVersionRuntimes(final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        return tenantModule.getShortcutService().viewVersionRuntimes(tenantId, versionId)
                .flatMap(versionRuntimes -> Multi.createFrom().iterable(versionRuntimes)
                        .onItem().transformToUniAndConcatenate(versionRuntime -> {
                            final var runtimeId = versionRuntime.getRuntimeId();
                            return runtimeModule.getShortcutService().countRuntimeClients(runtimeId)
                                    .map(count -> count > 0);
                        })
                        .collect().asList()
                        .map(result -> {
                            // Checking that all runtimes are without clients
                            return result.stream().allMatch(r -> r.equals(Boolean.FALSE));
                        }));
    }
}

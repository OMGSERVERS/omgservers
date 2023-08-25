package com.omgservers.application.module.versionModule.impl.operation.deployConfigurationOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.versionModule.impl.operation.getVersionConfigOperation.GetVersionConfigOperation;
import com.omgservers.model.version.VersionModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeployConfigurationOperationImpl implements DeployConfigurationOperation {

    final TenantModule tenantModule;
    final GetVersionConfigOperation getVersionConfigOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<VersionModel> deployConfiguration(VersionModel version) {
        return null;
//        final var tenant = version.getTenant();
//        final var project = version.getProject();
//        final var config = getVersionConfigOperation.getVersionConfig(version);
//        return syncProjectStages(tenant, project, config)
//                .flatMap(ignored -> cleanProjectStages(tenant, project, config))
//                .replaceWith(() -> {
//                    version.setStatus(VersionStatusEnum.DEPLOYED);
//                    return version;
//                });
    }

//    Uni<Void> syncProjectStages(final String tenant,
//                                final Long projectId,
//                                final VersionConfigModel config) {
//        final var syncStageRequests = config.getStages().stream()
//                .map(stageConfig -> {
//                    final var stage = StageModel.create(project, stageConfig.getName(), StageConfigModel.create());
//                    final var syncStageServiceRequest = new SyncStageServiceRequest(tenant, stage);
//                    return tenantModule.getStageInternalService().syncStage(syncStageServiceRequest);
//                })
//                .toList();
//
//        return Uni.join().all(syncStageRequests).andFailFast()
//                .replaceWithVoid();
//    }
//
//    Uni<Void> cleanProjectStages(final String tenant,
//                                 final Long projectId,
//                                 final VersionConfigModel config) {
//        return tenantModule.getStageInternalService()
//                .getProjectStages(new GetProjectStagesServiceRequest(tenant, project))
//                .map(GetProjectStagesServiceResponse::getStages)
//                .flatMap(projectStages -> {
//                    final var versionStages = config.getStages().stream()
//                            .map(VersionStageModel::getName)
//                            .toList();
//                    final var wastedStages = projectStages.stream()
//                            .filter(stage -> !versionStages.contains(stage.getName()))
//                            .map(stage -> stage.getUuid())
//                            .toList();
//
//                    if (wastedStages.size() > 0) {
//                        final var deleteStagesServiceRequest = new DeleteStagesServiceRequest(tenant, wastedStages);
//                        return tenantModule.getStageInternalService().deleteStages(deleteStagesServiceRequest)
//                                .replaceWithVoid();
//                    } else {
//                        return Uni.createFrom().voidItem();
//                    }
//                });
//    }
}

package com.omgservers.service.handler.internal;

import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestQualifierEnum;
import com.omgservers.schema.module.tenant.GetStageRequest;
import com.omgservers.schema.module.tenant.GetStageResponse;
import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.VersionBuildingRequestedEventBodyModel;
import com.omgservers.service.factory.tenant.VersionJenkinsRequestModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.service.jenkins.JenkinsService;
import com.omgservers.service.service.jenkins.dto.RunLuaJitRuntimeBuilderV1Request;
import com.omgservers.service.service.jenkins.dto.RunLuaJitRuntimeBuilderV1Response;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionBuildingRequestedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final JenkinsService jenkinsService;

    final VersionJenkinsRequestModelFactory versionJenkinsRequestModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.VERSION_BUILDING_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (VersionBuildingRequestedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var versionId = body.getVersionId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    final var stageId = version.getStageId();
                    return getStage(tenantId, stageId)
                            .flatMap(stage -> {
                                final var projectId = stage.getProjectId();
                                log.info("Version building was requested, version={}/{}", tenantId, versionId);

                                final var idempotencyKey = event.getId().toString();

                                // TODO: detect job qualifier based on version
                                return buildLuaJitRuntime(projectId, version, idempotencyKey);
                            });
                })
                .replaceWithVoid();
    }

    Uni<StageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetStageRequest(tenantId, id);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }

    Uni<VersionModel> getVersion(Long tenantId, Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Void> buildLuaJitRuntime(final Long projectId,
                                 final VersionModel version,
                                 final String idempotencyKey) {
        return runLuaJitRuntimeBuilderV1(projectId, version)
                .flatMap(buildNumber -> syncVersionJenkinsRequest(version,
                        VersionJenkinsRequestQualifierEnum.LUAJIT_RUNTIME_BUILDER_V1,
                        buildNumber,
                        idempotencyKey))
                .replaceWithVoid();
    }

    Uni<Integer> runLuaJitRuntimeBuilderV1(final Long projectId,
                                           final VersionModel version) {
        final var tenantId = version.getTenantId();
        final var stageId = version.getStageId();
        final var versionId = version.getId();
        final var groupId = String.format("omgservers/%d/%d/%d", tenantId, projectId, stageId);
        final var containerName = "universal";
        final var base64Archive = version.getBase64Archive();
        final var request = new RunLuaJitRuntimeBuilderV1Request(groupId,
                containerName,
                versionId.toString(),
                base64Archive);

        return jenkinsService.runLuaJitRuntimeBuilderV1(request)
                .map(RunLuaJitRuntimeBuilderV1Response::getBuildNumber);
    }

    Uni<Boolean> syncVersionJenkinsRequest(final VersionModel version,
                                           final VersionJenkinsRequestQualifierEnum qualifier,
                                           final Integer buildNumber,
                                           final String idempotencyKey) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var versionJenkinsRequest = versionJenkinsRequestModelFactory.create(tenantId,
                versionId,
                qualifier,
                buildNumber,
                idempotencyKey);

        final var request = new SyncVersionJenkinsRequestRequest(versionJenkinsRequest);
        return tenantModule.getVersionService().syncVersionJenkinsRequestWithIdempotency(request)
                .map(SyncVersionJenkinsRequestResponse::getCreated);
    }
}

package com.omgservers.service.handler.internal;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestQualifierEnum;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.VersionBuildingRequestedEventBodyModel;
import com.omgservers.service.factory.tenant.TenantJenkinsRequestModelFactory;
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

    final TenantJenkinsRequestModelFactory tenantJenkinsRequestModelFactory;

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
                    final var stageId = version.getProjectId();
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

    Uni<TenantStageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getTenantService().getStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Void> buildLuaJitRuntime(final Long projectId,
                                 final TenantVersionModel version,
                                 final String idempotencyKey) {
        return runLuaJitRuntimeBuilderV1(projectId, version)
                .flatMap(buildNumber -> syncVersionJenkinsRequest(version,
                        TenantJenkinsRequestQualifierEnum.LUAJIT_RUNTIME_BUILDER_V1,
                        buildNumber,
                        idempotencyKey))
                .replaceWithVoid();
    }

    Uni<Integer> runLuaJitRuntimeBuilderV1(final Long projectId,
                                           final TenantVersionModel version) {
        final var tenantId = version.getTenantId();
        final var stageId = version.getProjectId();
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

    Uni<Boolean> syncVersionJenkinsRequest(final TenantVersionModel version,
                                           final TenantJenkinsRequestQualifierEnum qualifier,
                                           final Integer buildNumber,
                                           final String idempotencyKey) {
        final var tenantId = version.getTenantId();
        final var versionId = version.getId();
        final var versionJenkinsRequest = tenantJenkinsRequestModelFactory.create(tenantId,
                versionId,
                qualifier,
                buildNumber,
                idempotencyKey);

        final var request = new SyncTenantJenkinsRequestRequest(versionJenkinsRequest);
        return tenantModule.getTenantService().syncVersionJenkinsRequestWithIdempotency(request)
                .map(SyncTenantJenkinsRequestResponse::getCreated);
    }
}

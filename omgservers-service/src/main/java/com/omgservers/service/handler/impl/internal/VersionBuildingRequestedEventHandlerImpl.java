package com.omgservers.service.handler.impl.internal;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestResponse;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
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

        final var idempotencyKey = event.getId().toString();

        return getTenantVersion(tenantId, versionId)
                .flatMap(tenantVersion -> {
                    final var tenantProjectId = tenantVersion.getProjectId();
                    log.info("Version building was requested, tenantVersion={}/{}", tenantId, versionId);
                    return buildLuaJitRuntime(tenantProjectId, tenantVersion, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<TenantProjectModel> getTenantProject(final Long tenantId, final Long id) {
        final var request = new GetTenantProjectRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantProject(request)
                .map(GetTenantProjectResponse::getTenantProject);
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Void> buildLuaJitRuntime(final Long tenantProjectId,
                                 final TenantVersionModel tenantVersion,
                                 final String idempotencyKey) {
        return runLuaJitRuntimeBuilderV1(tenantProjectId, tenantVersion)
                .flatMap(buildNumber -> syncTenantJenkinsRequest(tenantVersion,
                        TenantJenkinsRequestQualifierEnum.LUAJIT_RUNTIME_BUILDER_V1,
                        buildNumber,
                        idempotencyKey))
                .replaceWithVoid();
    }

    Uni<Integer> runLuaJitRuntimeBuilderV1(final Long tenantProjectId,
                                           final TenantVersionModel tenantVersion) {
        final var tenantId = tenantVersion.getTenantId();
        final var tenantStageId = tenantVersion.getProjectId();
        final var tenantVersionId = tenantVersion.getId();
        final var groupId = String.format("omgservers/%d/%d", tenantId, tenantProjectId);
        final var containerName = "universal";
        final var base64Archive = tenantVersion.getBase64Archive();
        final var request = new RunLuaJitRuntimeBuilderV1Request(groupId,
                containerName,
                tenantVersionId.toString(),
                base64Archive);

        return jenkinsService.runLuaJitRuntimeBuilderV1(request)
                .map(RunLuaJitRuntimeBuilderV1Response::getBuildNumber);
    }

    Uni<Boolean> syncTenantJenkinsRequest(final TenantVersionModel tenantVersion,
                                          final TenantJenkinsRequestQualifierEnum qualifier,
                                          final Integer buildNumber,
                                          final String idempotencyKey) {
        final var tenantId = tenantVersion.getTenantId();
        final var tenantVersionId = tenantVersion.getId();
        final var tenantJenkinsRequest = tenantJenkinsRequestModelFactory.create(tenantId,
                tenantVersionId,
                qualifier,
                buildNumber,
                idempotencyKey);

        final var request = new SyncTenantJenkinsRequestRequest(tenantJenkinsRequest);
        return tenantModule.getTenantService().syncTenantJenkinsRequestWithIdempotency(request)
                .map(SyncTenantJenkinsRequestResponse::getCreated);
    }
}

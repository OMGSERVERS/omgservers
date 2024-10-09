package com.omgservers.service.handler.impl.internal;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantFilesArchive.FindTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantFilesArchive.FindTenantFilesArchiveResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.SyncTenantJenkinsRequestResponse;
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
        final var tenantVersionId = body.getTenantVersionId();

        final var idempotencyKey = event.getId().toString();

        return getTenantVersion(tenantId, tenantVersionId)
                .flatMap(tenantVersion -> {
                    final var versionProjectId = tenantVersion.getProjectId();
                    return findTenantFilesArchive(tenantId, tenantVersionId)
                            .flatMap(tenantFilesArchive -> {
                                log.info("Version building was requested, tenantVersion={}/{}", tenantId,
                                        tenantVersionId);

                                return buildLuaJitRuntime(versionProjectId, tenantFilesArchive, idempotencyKey);
                            });
                });
    }

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantModule.getService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<TenantFilesArchiveModel> findTenantFilesArchive(Long tenantId, Long tenantVersionId) {
        final var request = new FindTenantFilesArchiveRequest(tenantId, tenantVersionId);
        return tenantModule.getService().findTenantFilesArchive(request)
                .map(FindTenantFilesArchiveResponse::getTenantFilesArchive);
    }

    Uni<Void> buildLuaJitRuntime(final Long tenantProjectId,
                                 final TenantFilesArchiveModel tenantFilesArchive,
                                 final String idempotencyKey) {
        return runLuaJitRuntimeBuilderV1(tenantProjectId, tenantFilesArchive)
                .flatMap(buildNumber -> createTenantJenkinsRequest(tenantFilesArchive,
                        TenantJenkinsRequestQualifierEnum.LUAJIT_RUNTIME_BUILDER_V1,
                        buildNumber,
                        idempotencyKey))
                .replaceWithVoid();
    }

    Uni<Integer> runLuaJitRuntimeBuilderV1(final Long tenantProjectId,
                                           final TenantFilesArchiveModel tenantFilesArchive) {
        final var tenantId = tenantFilesArchive.getTenantId();
        final var tenantVersionId = tenantFilesArchive.getVersionId();
        final var groupId = String.format("omgservers/%d/%d", tenantId, tenantProjectId);
        final var containerName = "universal";
        final var base64Archive = tenantFilesArchive.getBase64Archive();
        final var request = new RunLuaJitRuntimeBuilderV1Request(groupId,
                containerName,
                tenantVersionId.toString(),
                base64Archive);

        return jenkinsService.runLuaJitRuntimeBuilderV1(request)
                .map(RunLuaJitRuntimeBuilderV1Response::getBuildNumber);
    }

    Uni<Boolean> createTenantJenkinsRequest(final TenantFilesArchiveModel tenantFilesArchive,
                                            final TenantJenkinsRequestQualifierEnum qualifier,
                                            final Integer buildNumber,
                                            final String idempotencyKey) {
        final var tenantId = tenantFilesArchive.getTenantId();
        final var tenantVersionId = tenantFilesArchive.getVersionId();
        final var tenantJenkinsRequest = tenantJenkinsRequestModelFactory.create(tenantId,
                tenantVersionId,
                qualifier,
                buildNumber,
                idempotencyKey);

        final var request = new SyncTenantJenkinsRequestRequest(tenantJenkinsRequest);
        return tenantModule.getService().syncTenantJenkinsRequestWithIdempotency(request)
                .map(SyncTenantJenkinsRequestResponse::getCreated);
    }
}

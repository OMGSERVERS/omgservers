package com.omgservers.service.handler.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.jenkins.RunLuaJitWorkerBuilderV1Request;
import com.omgservers.model.dto.jenkins.RunLuaJitWorkerBuilderV1Response;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestRequest;
import com.omgservers.model.dto.tenant.versionJenkinsRequest.SyncVersionJenkinsRequestResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.internal.VersionBuildingCheckingRequestedEventBodyModel;
import com.omgservers.model.event.body.internal.VersionBuildingRequestedEventBodyModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestQualifierEnum;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.tenant.VersionJenkinsRequestModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.integration.jenkins.JenkinsIntegration;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionBuildingRequestedEventHandlerImpl implements EventHandler {
    static private final int INITIAL_CHECKING_INTERVAL_IN_SECONDS = 1;

    final JenkinsIntegration jenkinsIntegration;
    final TenantModule tenantModule;
    final SystemModule systemModule;

    final VersionJenkinsRequestModelFactory versionJenkinsRequestModelFactory;

    final EventModelFactory eventModelFactory;
    final ObjectMapper objectMapper;

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
                    log.info("Version building was requested, version={}/{}", tenantId, versionId);

                    final var idempotencyKey = event.getId().toString();

                    // TODO: detect job qualifier based on version
                    return buildLuaJitWorker(version, idempotencyKey)
                            .flatMap(created -> requestVersionChecking(tenantId, versionId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<VersionModel> getVersion(Long tenantId, Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Void> buildLuaJitWorker(final VersionModel version,
                                final String idempotencyKey) {
        return runLuaJitWorkerBuilderV1(version)
                .flatMap(buildNumber -> syncVersionJenkinsRequest(version,
                        VersionJenkinsRequestQualifierEnum.LUAJIT_WORKER_BUILDER_V1,
                        buildNumber,
                        idempotencyKey))
                .replaceWithVoid();
    }

    Uni<Integer> runLuaJitWorkerBuilderV1(VersionModel version) {
        final var versionId = version.getId();
        final var groupId = "omgservers/tenant/" + version.getTenantId();
        final var containerName = "universal";
        final var base64Archive = version.getBase64Archive();
        final var request = new RunLuaJitWorkerBuilderV1Request(groupId,
                containerName,
                versionId.toString(),
                base64Archive);

        return jenkinsIntegration.getJenkinsService().runLuaJitWorkerBuilderV1(request)
                .map(RunLuaJitWorkerBuilderV1Response::getBuildNumber);
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

    Uni<Boolean> requestVersionChecking(final Long tenantId,
                                        final Long versionId,
                                        final String idempotencyKey) {
        final var eventBody = new VersionBuildingCheckingRequestedEventBodyModel(tenantId,
                versionId,
                INITIAL_CHECKING_INTERVAL_IN_SECONDS);
        final var eventModel = eventModelFactory.create(eventBody,
                idempotencyKey + "/" + eventBody.getQualifier());
        eventModel.setDelayed(Instant.now().plus(Duration.ofSeconds(INITIAL_CHECKING_INTERVAL_IN_SECONDS)));

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}

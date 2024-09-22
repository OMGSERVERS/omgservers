package com.omgservers.service.service.task.impl.method.executeJenkinsRequestTask;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantImageRef.TenantImageRefQualifierEnum;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import com.omgservers.schema.module.tenant.tenantImageRef.SyncTenantImageRefRequest;
import com.omgservers.schema.module.tenant.tenantImageRef.SyncTenantImageRefResponse;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestResponse;
import com.omgservers.service.event.body.internal.VersionBuildingFailedEventBodyModel;
import com.omgservers.service.event.body.internal.VersionBuildingFinishedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.tenant.TenantImageRefModelFactory;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.service.event.EventService;
import com.omgservers.service.service.event.dto.SyncEventRequest;
import com.omgservers.service.service.event.dto.SyncEventResponse;
import com.omgservers.service.service.jenkins.JenkinsService;
import com.omgservers.service.service.jenkins.dto.GetLuaJitRuntimeBuilderV1Request;
import com.omgservers.service.service.jenkins.dto.GetLuaJitRuntimeBuilderV1Response;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class JenkinsRequestTaskImpl {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    final JenkinsService jenkinsService;
    final EventService eventService;

    final TenantImageRefModelFactory tenantImageRefModelFactory;
    final EventModelFactory eventModelFactory;

    public Uni<Boolean> executeTask(final Long tenantId, final Long jenkinsRequestId) {
        return getTenantJenkinsRequest(tenantId, jenkinsRequestId)
                .flatMap(jenkinsRequest -> handleJenkinsRequest(jenkinsRequest)
                        .replaceWith(Boolean.TRUE));
    }

    Uni<TenantJenkinsRequestModel> getTenantJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantJenkinsRequestRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantJenkinsRequest(request)
                .map(GetTenantJenkinsRequestResponse::getTenantJenkinsRequest);
    }

    Uni<Void> handleJenkinsRequest(final TenantJenkinsRequestModel versionJenkinsRequest) {
        return checkVersionJenkinsRequest(versionJenkinsRequest)
                .flatMap(result -> switch (result) {
                    case IN_PROGRESS -> {
                        log.info("Jenkins job is still in progress, jenkinsRequest={}/{}",
                                versionJenkinsRequest.getTenantId(), versionJenkinsRequest.getId());
                        yield Uni.createFrom().voidItem();
                    }
                    case FINISHED -> syncVersionBuildingFinished(versionJenkinsRequest)
                            .replaceWithVoid();
                    case FAILED -> syncVersionBuildingFailed(versionJenkinsRequest)
                            .replaceWithVoid();
                });
    }

    Uni<JenkinsRequestResultEnum> checkVersionJenkinsRequest(final TenantJenkinsRequestModel versionJenkinsRequest) {
        final var tenantId = versionJenkinsRequest.getTenantId();
        final var versionId = versionJenkinsRequest.getVersionId();
        final var qualifier = versionJenkinsRequest.getQualifier();
        final var buildNumber = versionJenkinsRequest.getBuildNumber();
        final var idempotencyKey = versionJenkinsRequest.getId().toString();
        log.info("Checking jenkins request, versionJenkinsRequest={}/{}, versionId={}, qualifier={}, buildNumber={}",
                tenantId,
                versionJenkinsRequest.getId(),
                versionId,
                qualifier,
                buildNumber);

        return getLuaJitRuntimeBuilderV1Request(buildNumber)
                .flatMap(imageId -> {
                    log.info("Jenkins job was finished, qualifier={}, buildNumber={}, imageId={}",
                            qualifier, buildNumber, imageId);
                    return syncTenantImageRef(versionJenkinsRequest, imageId, idempotencyKey);
                })
                .map(created -> JenkinsRequestResultEnum.FINISHED)
                .onFailure(ServerSideBadRequestException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.JENKINS_JOB_UNFINISHED)) {
                            return Uni.createFrom().item(JenkinsRequestResultEnum.IN_PROGRESS);
                        }
                    }

                    log.warn("Jenkins job failed, {}", t.getMessage());
                    return Uni.createFrom().item(JenkinsRequestResultEnum.FAILED);
                });
    }

    Uni<String> getLuaJitRuntimeBuilderV1Request(final Integer buildNumber) {
        final var request = new GetLuaJitRuntimeBuilderV1Request(buildNumber);
        return jenkinsService.getLuaJitRuntimeBuilderV1(request)
                .map(GetLuaJitRuntimeBuilderV1Response::getImageId);
    }

    Uni<Boolean> syncTenantImageRef(final TenantJenkinsRequestModel versionJenkinsRequest,
                                    final String imageId,
                                    final String idempotencyKey) {

        final var tenantId = versionJenkinsRequest.getTenantId();
        final var versionId = versionJenkinsRequest.getVersionId();

        final var versionImageRef = tenantImageRefModelFactory.create(tenantId,
                versionId,
                TenantImageRefQualifierEnum.UNIVERSAL,
                imageId,
                idempotencyKey);

        final var request = new SyncTenantImageRefRequest(versionImageRef);
        return tenantModule.getTenantService().syncTenantImageRefWithIdempotency(request)
                .map(SyncTenantImageRefResponse::getCreated);
    }

    Uni<Boolean> syncVersionBuildingFinished(final TenantJenkinsRequestModel versionJenkinsRequest) {
        final var tenantId = versionJenkinsRequest.getTenantId();
        final var versionId = versionJenkinsRequest.getVersionId();
        final var idempotencyKey = versionJenkinsRequest.getId().toString();

        final var eventBody = new VersionBuildingFinishedEventBodyModel(tenantId, versionId);
        final var eventModel = eventModelFactory.create(eventBody, idempotencyKey);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }


    Uni<Boolean> syncVersionBuildingFailed(final TenantJenkinsRequestModel versionJenkinsRequest) {
        final var tenantId = versionJenkinsRequest.getTenantId();
        final var versionId = versionJenkinsRequest.getVersionId();
        final var idempotencyKey = versionJenkinsRequest.getId().toString();
        final var eventBody = new VersionBuildingFailedEventBodyModel(tenantId, versionId);
        final var eventModel = eventModelFactory.create(eventBody, idempotencyKey);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }

    enum JenkinsRequestResultEnum {
        IN_PROGRESS,
        FINISHED,
        FAILED
    }
}

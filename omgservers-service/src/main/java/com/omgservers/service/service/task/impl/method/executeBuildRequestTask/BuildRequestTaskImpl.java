package com.omgservers.service.service.task.impl.method.executeBuildRequestTask;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestResponse;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageResponse;
import com.omgservers.service.event.body.internal.VersionBuildingFailedEventBodyModel;
import com.omgservers.service.event.body.internal.VersionBuildingFinishedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.tenant.TenantImageModelFactory;
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
public class BuildRequestTaskImpl {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;

    final JenkinsService jenkinsService;
    final EventService eventService;

    final TenantImageModelFactory tenantImageModelFactory;
    final EventModelFactory eventModelFactory;

    public Uni<Boolean> execute(final Long tenantId, final Long tenantBuildRequestId) {
        return getTenantBuildRequest(tenantId, tenantBuildRequestId)
                .flatMap(tenantBuildRequest -> handleTenantBuildRequest(tenantBuildRequest)
                        .replaceWith(Boolean.TRUE));
    }

    Uni<TenantBuildRequestModel> getTenantBuildRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantBuildRequestRequest(tenantId, id);
        return tenantModule.getService().getTenantBuildRequest(request)
                .map(GetTenantBuildRequestResponse::getTenantBuildRequest);
    }

    Uni<Void> handleTenantBuildRequest(final TenantBuildRequestModel tenantBuildRequest) {
        final var qualifier = tenantBuildRequest.getQualifier();
        return switch (qualifier) {
            case JENKINS_LUAJIT_RUNTIME_BUILDER_V1 -> checkJenkinsBuildRequest(tenantBuildRequest)
                    .flatMap(result -> switch (result) {
                        case IN_PROGRESS -> {
                            log.debug("Build job is still in progress, tenantBuildRequest={}/{}",
                                    tenantBuildRequest.getTenantId(), tenantBuildRequest.getId());
                            yield Uni.createFrom().voidItem();
                        }
                        case FINISHED -> syncVersionBuildingFinished(tenantBuildRequest)
                                .replaceWithVoid();
                        case FAILED -> syncVersionBuildingFailed(tenantBuildRequest)
                                .replaceWithVoid();
                    });
        };
    }

    Uni<JenkinsRequestResultEnum> checkJenkinsBuildRequest(final TenantBuildRequestModel tenantBuildRequest) {
        final var tenantId = tenantBuildRequest.getTenantId();
        final var tenantVersionId = tenantBuildRequest.getVersionId();
        final var qualifier = tenantBuildRequest.getQualifier();
        final var buildNumber = tenantBuildRequest.getBuildNumber();
        final var idempotencyKey = tenantBuildRequest.getId().toString();
        log.debug("Checking jenkins build request, " +
                        "tenantBuildRequest={}/{}, tenantVersionId={}, qualifier={}, buildNumber={}",
                tenantId,
                tenantBuildRequest.getId(),
                tenantVersionId,
                qualifier,
                buildNumber);

        return getLuaJitRuntimeBuilderV1Request(buildNumber)
                .flatMap(imageId -> {
                    log.debug("Jenkins job \"{}\" was finished", buildNumber);
                    return syncTenantImage(tenantBuildRequest, imageId, idempotencyKey);
                })
                .map(created -> JenkinsRequestResultEnum.FINISHED)
                .onFailure(ServerSideBadRequestException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.JENKINS_JOB_UNFINISHED)) {
                            return Uni.createFrom().item(JenkinsRequestResultEnum.IN_PROGRESS);
                        }
                    }

                    log.warn("Jenkins job \"{}\" failed, {}", buildNumber, t.getMessage());
                    return Uni.createFrom().item(JenkinsRequestResultEnum.FAILED);
                });
    }

    Uni<String> getLuaJitRuntimeBuilderV1Request(final Integer buildNumber) {
        final var request = new GetLuaJitRuntimeBuilderV1Request(buildNumber);
        return jenkinsService.getLuaJitRuntimeBuilderV1(request)
                .map(GetLuaJitRuntimeBuilderV1Response::getImageId);
    }

    Uni<Boolean> syncTenantImage(final TenantBuildRequestModel tenantBuildRequest,
                                 final String imageId,
                                 final String idempotencyKey) {

        final var tenantId = tenantBuildRequest.getTenantId();
        final var versionId = tenantBuildRequest.getVersionId();

        final var versionImage = tenantImageModelFactory.create(tenantId,
                versionId,
                TenantImageQualifierEnum.UNIVERSAL,
                imageId,
                idempotencyKey);

        final var request = new SyncTenantImageRequest(versionImage);
        return tenantModule.getService().syncTenantImageWithIdempotency(request)
                .map(SyncTenantImageResponse::getCreated);
    }

    Uni<Boolean> syncVersionBuildingFinished(final TenantBuildRequestModel tenantBuildRequest) {
        final var tenantId = tenantBuildRequest.getTenantId();
        final var versionId = tenantBuildRequest.getVersionId();
        final var idempotencyKey = tenantBuildRequest.getId().toString();

        final var eventBody = new VersionBuildingFinishedEventBodyModel(tenantId, versionId);
        final var eventModel = eventModelFactory.create(eventBody, idempotencyKey);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }


    Uni<Boolean> syncVersionBuildingFailed(final TenantBuildRequestModel tenantBuildRequest) {
        final var tenantId = tenantBuildRequest.getTenantId();
        final var versionId = tenantBuildRequest.getVersionId();
        final var idempotencyKey = tenantBuildRequest.getId().toString();
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

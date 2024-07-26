package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.developer.DeployVersionDeveloperRequest;
import com.omgservers.model.dto.developer.DeployVersionDeveloperResponse;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.HasStagePermissionRequest;
import com.omgservers.model.dto.tenant.HasStagePermissionResponse;
import com.omgservers.model.event.body.internal.VersionDeploymentRequestedEventBodyModel;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.tenant.VersionImageRefModelFactory;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeployVersionMethodImpl implements DeployVersionMethod {

    final TenantModule tenantModule;
    final SystemModule systemModule;

    final VersionImageRefModelFactory versionImageRefModelFactory;
    final EventModelFactory eventModelFactory;

    final ObjectMapper objectMapper;
    final JsonWebToken jwt;

    @Override
    public Uni<DeployVersionDeveloperResponse> deployVersion(final DeployVersionDeveloperRequest request) {
        log.debug("Deploy version, request={}", request);

        final var tenantId = request.getTenantId();
        final var versionId = request.getVersionId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    final var stageId = version.getStageId();
                    final var userId = Long.valueOf(jwt.getClaim(Claims.sub));
                    return checkVersionManagementPermission(tenantId, stageId, userId)
                            .flatMap(voidItem -> requestVersionDeployment(tenantId, versionId))
                            .replaceWith(new DeployVersionDeveloperResponse());
                });
    }

    Uni<VersionModel> getVersion(final Long tenantId, final Long versionId) {
        final var request = new GetVersionRequest(tenantId, versionId);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Void> checkVersionManagementPermission(final Long tenantId,
                                               final Long stageId,
                                               final Long userId) {
        final var permission = StagePermissionEnum.VERSION_MANAGEMENT;
        final var hasStagePermissionServiceRequest = new HasStagePermissionRequest(tenantId,
                stageId,
                userId,
                permission);
        return tenantModule.getStageService().hasStagePermission(hasStagePermissionServiceRequest)
                .map(HasStagePermissionResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, " +
                                                "tenantId=%d, stageId=%d, userId=%d, permission=%s",
                                        tenantId, stageId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<Boolean> requestVersionDeployment(final Long tenantId,
                                          final Long versionId) {
        final var eventBody = new VersionDeploymentRequestedEventBodyModel(tenantId, versionId);
        // Version deployment can be requested only once
        final var idempotencyKey = tenantId + "/" + versionId + "/" + eventBody.getQualifier();
        final var eventModel = eventModelFactory.create(eventBody, idempotencyKey);

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}

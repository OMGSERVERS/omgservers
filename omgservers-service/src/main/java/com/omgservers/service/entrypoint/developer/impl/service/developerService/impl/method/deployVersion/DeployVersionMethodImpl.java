package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperResponse;
import com.omgservers.schema.event.body.internal.VersionDeploymentRequestedEventBodyModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.stagePermission.StagePermissionEnum;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import com.omgservers.schema.module.tenant.HasStagePermissionRequest;
import com.omgservers.schema.module.tenant.HasStagePermissionResponse;
import com.omgservers.schema.service.system.SyncEventRequest;
import com.omgservers.schema.service.system.SyncEventResponse;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.tenant.VersionImageRefModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.server.security.ServiceSecurityAttributes;
import com.omgservers.service.server.service.event.EventService;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeployVersionMethodImpl implements DeployVersionMethod {

    final TenantModule tenantModule;

    final EventService eventService;

    final VersionImageRefModelFactory versionImageRefModelFactory;
    final EventModelFactory eventModelFactory;

    final SecurityIdentity securityIdentity;
    final ObjectMapper objectMapper;

    @Override
    public Uni<DeployVersionDeveloperResponse> deployVersion(final DeployVersionDeveloperRequest request) {
        log.debug("Deploy version, request={}", request);

        final var tenantId = request.getTenantId();
        final var versionId = request.getVersionId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    final var stageId = version.getStageId();
                    final var userId =
                            securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

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
        return eventService.syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}

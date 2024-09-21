package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperResponse;
import com.omgservers.service.event.body.internal.VersionDeploymentRequestedEventBodyModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantStagePermission.VerifyTenantStagePermissionExistsResponse;
import com.omgservers.service.service.event.dto.SyncEventRequest;
import com.omgservers.service.service.event.dto.SyncEventResponse;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.factory.tenant.TenantImageRefModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.security.ServiceSecurityAttributes;
import com.omgservers.service.service.event.EventService;
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

    final TenantImageRefModelFactory tenantImageRefModelFactory;
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
                    final var stageId = version.getProjectId();
                    final var userId =
                            securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

                    return checkVersionManagementPermission(tenantId, stageId, userId)
                            .flatMap(voidItem -> requestVersionDeployment(tenantId, versionId))
                            .replaceWith(new DeployVersionDeveloperResponse());
                });
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long versionId) {
        final var request = new GetTenantVersionRequest(tenantId, versionId);
        return tenantModule.getTenantService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }

    Uni<Void> checkVersionManagementPermission(final Long tenantId,
                                               final Long stageId,
                                               final Long userId) {
        final var permission = TenantStagePermissionEnum.VERSION_MANAGEMENT;
        final var hasStagePermissionServiceRequest = new VerifyTenantStagePermissionExistsRequest(tenantId,
                stageId,
                userId,
                permission);
        return tenantModule.getTenantService().verifyTenantStagePermissionExists(hasStagePermissionServiceRequest)
                .map(VerifyTenantStagePermissionExistsResponse::getExists)
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

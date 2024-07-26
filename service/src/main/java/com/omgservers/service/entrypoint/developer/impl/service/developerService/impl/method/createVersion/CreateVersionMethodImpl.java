package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.model.dto.tenant.HasStagePermissionRequest;
import com.omgservers.model.dto.tenant.HasStagePermissionResponse;
import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.tenant.VersionModelFactory;
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
class CreateVersionMethodImpl implements CreateVersionMethod {

    final TenantModule tenantModule;

    final VersionModelFactory versionModelFactory;

    final ObjectMapper objectMapper;
    final JsonWebToken jwt;

    @Override
    public Uni<CreateVersionDeveloperResponse> createVersion(final CreateVersionDeveloperRequest request) {
        log.debug("Create version, request={}", request);

        final var userId = Long.valueOf(jwt.getClaim(Claims.sub));

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var versionConfig = request.getVersionConfig();

        return checkVersionManagementPermission(tenantId, stageId, userId)
                .flatMap(voidItem -> createVersion(tenantId, stageId, versionConfig))
                .map(VersionModel::getId)
                .map(CreateVersionDeveloperResponse::new);
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

    Uni<VersionModel> createVersion(final Long tenantId,
                                    final Long stageId,
                                    final VersionConfigModel versionConfig) {
        // TODO: fix empty archive field
        final var version = versionModelFactory.create(tenantId, stageId, versionConfig, "");
        final var request = new SyncVersionRequest(version);
        return tenantModule.getVersionService().syncVersion(request)
                .replaceWith(version);
    }
}

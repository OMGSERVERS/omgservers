package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.uploadVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.developer.UploadVersionDeveloperRequest;
import com.omgservers.model.dto.developer.UploadVersionDeveloperResponse;
import com.omgservers.model.dto.tenant.HasStagePermissionRequest;
import com.omgservers.model.dto.tenant.HasStagePermissionResponse;
import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.entrypoint.developer.impl.operation.EncodeFilesOperation;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
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

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UploadVersionMethodImpl implements UploadVersionMethod {

    private static final String CONFIG_JSON = "config.json";
    private static final String PROJECT_ZIP = "project.zip";

    final TenantModule tenantModule;

    final EncodeFilesOperation encodeFilesOperation;

    final VersionModelFactory versionModelFactory;

    final ObjectMapper objectMapper;
    final JsonWebToken jwt;

    @Override
    public Uni<UploadVersionDeveloperResponse> uploadVersion(final UploadVersionDeveloperRequest request) {
        log.debug("Upload version, request={}", request);

        final var userId = Long.valueOf(jwt.getClaim(Claims.upn));

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var versionConfig = getVersionConfig(request);
        final var base64Archive = getBase64Archive(request);

        return checkVersionManagementPermission(tenantId, stageId, userId)
                .flatMap(voidItem -> createVersion(tenantId, stageId, versionConfig, base64Archive))
                .map(VersionModel::getId)
                .map(UploadVersionDeveloperResponse::new);
    }

    VersionConfigModel getVersionConfig(final UploadVersionDeveloperRequest request) {
        return request.getFiles().stream()
                .filter(fileUpload -> fileUpload.name().equals(CONFIG_JSON))
                .map(fileUpload -> {
                    try {
                        final var fileContent = Files.readString(fileUpload.filePath());
                        final var configModel = objectMapper.readValue(fileContent, VersionConfigModel.class);
                        return configModel;
                    } catch (IOException e) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.CONFIG_JSON_WRONG,
                                String.format(CONFIG_JSON + " was not parsed, request=%s, %s",
                                        request, e.getMessage()), e);
                    }
                })
                .findFirst()
                .orElseThrow(() -> new ServerSideBadRequestException(ExceptionQualifierEnum.CONFIG_JSON_NOT_FOUND,
                        CONFIG_JSON + " was not found, request=" + request));
    }

    String getBase64Archive(final UploadVersionDeveloperRequest request) {
        return request.getFiles().stream()
                .filter(fileUpload -> fileUpload.name().equals(PROJECT_ZIP))
                .map(fileUpload -> {
                    try {
                        final var fileContent = Files.readAllBytes(fileUpload.filePath());
                        final var base64Archive = Base64.getEncoder().encodeToString(fileContent);
                        return base64Archive;
                    } catch (IOException e) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.PROJECT_ZIP_WRONG,
                                String.format(PROJECT_ZIP + " was not parsed, request=%s, %s",
                                        request, e.getMessage()), e);
                    }
                })
                .findFirst()
                .orElseThrow(() -> new ServerSideBadRequestException(ExceptionQualifierEnum.PROJECT_ZIP_NOT_FOUND,
                        PROJECT_ZIP + " was not found, request=" + request));
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
                                    final VersionConfigModel versionConfig,
                                    final String base64Archive) {
        final var version = versionModelFactory.create(tenantId, stageId, versionConfig, base64Archive);
        final var request = new SyncVersionRequest(version);
        return tenantModule.getVersionService().syncVersion(request)
                .replaceWith(version);
    }
}

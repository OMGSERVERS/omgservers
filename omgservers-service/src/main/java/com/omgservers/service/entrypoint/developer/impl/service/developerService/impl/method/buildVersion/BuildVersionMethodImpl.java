package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.buildVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.BuildVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.BuildVersionDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.service.entrypoint.developer.impl.operation.encodeFiles.EncodeFilesOperation;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.security.ServiceSecurityAttributes;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BuildVersionMethodImpl implements BuildVersionMethod {

    private static final String CONFIG_JSON = "config.json";
    private static final String VERSION_ZIP = "version.zip";

    final TenantModule tenantModule;

    final EncodeFilesOperation encodeFilesOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;

    final SecurityIdentity securityIdentity;
    final ObjectMapper objectMapper;

    @Override
    public Uni<BuildVersionDeveloperResponse> buildVersion(final BuildVersionDeveloperRequest request) {
        log.debug("Build version, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var projectId = request.getProjectId();
        final var versionConfig = getVersionConfig(request);
        final var base64Archive = getBase64Archive(request);

        return checkVersionManagementPermission(tenantId, projectId, userId)
                .flatMap(voidItem -> createVersion(tenantId, projectId, versionConfig, base64Archive))
                .map(TenantVersionModel::getId)
                .map(BuildVersionDeveloperResponse::new);
    }

    TenantVersionConfigDto getVersionConfig(final BuildVersionDeveloperRequest request) {
        return request.getFiles().stream()
                .filter(fileUpload -> fileUpload.name().equals(CONFIG_JSON))
                .map(fileUpload -> {
                    try {
                        final var fileContent = Files.readString(fileUpload.filePath());
                        final var configModel = objectMapper.readValue(fileContent, TenantVersionConfigDto.class);
                        return configModel;
                    } catch (IOException e) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_CONFIG_JSON,
                                String.format(CONFIG_JSON + " was not parsed, request=%s, %s",
                                        request, e.getMessage()), e);
                    }
                })
                .findFirst()
                .orElseThrow(() -> new ServerSideBadRequestException(ExceptionQualifierEnum.CONFIG_JSON_NOT_FOUND,
                        CONFIG_JSON + " was not found, request=" + request));
    }

    String getBase64Archive(final BuildVersionDeveloperRequest request) {
        return request.getFiles().stream()
                .filter(fileUpload -> fileUpload.name().equals(VERSION_ZIP))
                .map(fileUpload -> {
                    try {
                        final var fileContent = Files.readAllBytes(fileUpload.filePath());
                        final var base64Archive = Base64.getEncoder().encodeToString(fileContent);
                        return base64Archive;
                    } catch (IOException e) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_VERSION_ZIP,
                                String.format(VERSION_ZIP + " was not parsed, request=%s, %s",
                                        request, e.getMessage()), e);
                    }
                })
                .findFirst()
                .orElseThrow(() -> new ServerSideBadRequestException(ExceptionQualifierEnum.VERSION_ZIP_NOT_FOUND,
                        VERSION_ZIP + " was not found, request=" + request));
    }

    Uni<Void> checkVersionManagementPermission(final Long tenantId,
                                               final Long projectId,
                                               final Long userId) {
        final var permission = TenantProjectPermissionEnum.VERSION_MANAGEMENT;
        final var request = new VerifyTenantProjectPermissionExistsRequest(tenantId,
                projectId,
                userId,
                permission);
        return tenantModule.getTenantService().verifyTenantProjectPermissionExists(request)
                .map(VerifyTenantProjectPermissionExistsResponse::getExists)
                .invoke(exists -> {
                    if (!exists) {
                        throw new ServerSideForbiddenException(ExceptionQualifierEnum.PERMISSION_NOT_FOUND,
                                String.format("permission was not found, " +
                                                "tenantId=%d, projectId=%d, userId=%d, permission=%s",
                                        tenantId, projectId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<TenantVersionModel> createVersion(final Long tenantId,
                                          final Long projectId,
                                          final TenantVersionConfigDto versionConfig,
                                          final String base64Archive) {
        final var version = tenantVersionModelFactory.create(tenantId,
                projectId,
                versionConfig,
                base64Archive);
        final var request = new SyncTenantVersionRequest(version);
        return tenantModule.getTenantService().syncTenantVersion(request)
                .replaceWith(version);
    }
}

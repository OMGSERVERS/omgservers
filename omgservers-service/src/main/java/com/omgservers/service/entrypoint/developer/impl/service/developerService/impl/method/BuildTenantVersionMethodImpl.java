package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.BuildTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.BuildTenantVersionDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantVersion.SyncTenantVersionRequest;
import com.omgservers.service.entrypoint.developer.impl.operation.EncodeFilesOperation;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.exception.ServerSideBadRequestException;
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
class BuildTenantVersionMethodImpl implements BuildTenantVersionMethod {

    private static final String CONFIG_JSON = "config.json";
    private static final String VERSION_ZIP = "version.zip";

    final TenantModule tenantModule;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final EncodeFilesOperation encodeFilesOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;

    final SecurityIdentity securityIdentity;
    final ObjectMapper objectMapper;

    @Override
    public Uni<BuildTenantVersionDeveloperResponse> execute(final BuildTenantVersionDeveloperRequest request) {
        log.debug("Build tenant version, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var tenantProjectId = request.getTenantProjectId();
        final var versionConfig = getVersionConfig(request);
        final var base64Archive = getBase64Archive(request);

        final var permissionQualifier = TenantProjectPermissionQualifierEnum.VERSION_MANAGEMENT;
        return checkTenantProjectPermissionOperation.execute(tenantId, tenantProjectId, userId, permissionQualifier)
                .flatMap(voidItem -> createVersion(tenantId, tenantProjectId, versionConfig, base64Archive))
                .map(TenantVersionModel::getId)
                .map(BuildTenantVersionDeveloperResponse::new);
    }

    TenantVersionConfigDto getVersionConfig(final BuildTenantVersionDeveloperRequest request) {
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

    String getBase64Archive(final BuildTenantVersionDeveloperRequest request) {
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

    Uni<TenantVersionModel> createVersion(final Long tenantId,
                                          final Long tenantProjectId,
                                          final TenantVersionConfigDto versionConfig,
                                          final String base64Archive) {
        final var tenantVersion = tenantVersionModelFactory.create(tenantId,
                tenantProjectId,
                versionConfig,
                base64Archive);
        final var request = new SyncTenantVersionRequest(tenantVersion);
        return tenantModule.getTenantService().syncTenantVersion(request)
                .replaceWith(tenantVersion);
    }
}

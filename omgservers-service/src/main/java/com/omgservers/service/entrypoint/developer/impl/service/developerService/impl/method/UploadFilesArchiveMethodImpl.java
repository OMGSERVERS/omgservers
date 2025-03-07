package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantFilesArchive.SyncTenantFilesArchiveRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.entrypoint.developer.impl.operation.EncodeFilesOperation;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantProjectPermissionOperation;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantFilesArchiveModelFactory;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
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
class UploadFilesArchiveMethodImpl implements UploadFilesArchiveMethod {

    private static final String VERSION_ZIP = "version.zip";

    final TenantShard tenantShard;

    final CheckTenantProjectPermissionOperation checkTenantProjectPermissionOperation;
    final GetServiceConfigOperation getServiceConfigOperation;
    final GetIdByTenantOperation getIdByTenantOperation;
    final EncodeFilesOperation encodeFilesOperation;

    final TenantFilesArchiveModelFactory tenantFilesArchiveModelFactory;

    final SecurityIdentity securityIdentity;
    final ObjectMapper objectMapper;

    @Override
    public Uni<UploadFilesArchiveDeveloperResponse> execute(final UploadFilesArchiveDeveloperRequest request) {
        log.info("Requested, {}", request);

        if (!getServiceConfigOperation.getServiceConfig().featureFlags().builderEnabled()) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.FEATURE_DISABLED,
                    "Builder feature is disabled");
        }

        final var userId =
                securityIdentity.<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var tenantVersionId = request.getVersionId();
                    final var base64Archive = getBase64Archive(request);
                    return getTenantVersion(tenantId, tenantVersionId)
                            .flatMap(tenantVersion -> {
                                final var versionProjectId = tenantVersion.getProjectId();
                                final var permissionQualifier = TenantProjectPermissionQualifierEnum.VERSION_MANAGER;
                                return checkTenantProjectPermissionOperation.execute(tenantId,
                                                versionProjectId,
                                                userId,
                                                permissionQualifier)
                                        // The building process will run in the background.
                                        .flatMap(voidItem -> createTenantFilesArchive(tenantId, tenantVersionId,
                                                base64Archive))
                                        .map(TenantFilesArchiveModel::getId);
                            });
                })
                .map(UploadFilesArchiveDeveloperResponse::new);
    }

    String getBase64Archive(final UploadFilesArchiveDeveloperRequest request) {
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

    Uni<TenantVersionModel> getTenantVersion(Long tenantId, Long id) {
        final var request = new GetTenantVersionRequest(tenantId, id);
        return tenantShard.getService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }


    Uni<TenantFilesArchiveModel> createTenantFilesArchive(final Long tenantId,
                                                          final Long tenantVersionId,
                                                          final String base64Archive) {
        final var tenantFilesArchive = tenantFilesArchiveModelFactory.create(tenantId,
                tenantVersionId,
                base64Archive);
        final var request = new SyncTenantFilesArchiveRequest(tenantFilesArchive);
        return tenantShard.getService().syncTenantFilesArchive(request)
                .replaceWith(tenantFilesArchive);
    }
}

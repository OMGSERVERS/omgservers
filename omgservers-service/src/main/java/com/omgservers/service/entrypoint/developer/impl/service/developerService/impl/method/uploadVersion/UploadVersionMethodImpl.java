package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.uploadVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.UploadVersionDeveloperRequest;
import com.omgservers.model.dto.developer.UploadVersionDeveloperResponse;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.service.entrypoint.developer.impl.operation.EncodeFilesOperation;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createVersion.CreateVersionMethod;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
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
class UploadVersionMethodImpl implements UploadVersionMethod {

    private static final String CONFIG_JSON = "config.json";
    private static final String PROJECT_ZIP = "project.zip";

    final CreateVersionMethod createVersionMethod;

    final EncodeFilesOperation encodeFilesOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<UploadVersionDeveloperResponse> uploadVersion(final UploadVersionDeveloperRequest request) {
        return Uni.createFrom().item(request)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .flatMap(this::createRequest)
                .flatMap(createVersionMethod::createVersion)
                .map(createVersionDeveloperResponse ->
                        new UploadVersionDeveloperResponse(createVersionDeveloperResponse.getId()));
    }

    Uni<CreateVersionDeveloperRequest> createRequest(final UploadVersionDeveloperRequest request) {
        final var createVersionDeveloperRequest = new CreateVersionDeveloperRequest();
        createVersionDeveloperRequest.setTenantId(request.getTenantId());
        createVersionDeveloperRequest.setStageId(request.getStageId());

        final var config = getConfig(request);
        createVersionDeveloperRequest.setVersionConfig(config);

        final var base64Archive = getBase64Archive(request);
        createVersionDeveloperRequest.setBase64Archive(base64Archive);

        return Uni.createFrom().item(createVersionDeveloperRequest);
    }

    VersionConfigModel getConfig(final UploadVersionDeveloperRequest request) {
        return request.getFiles().stream()
                .filter(fileUpload -> fileUpload.name().equals(CONFIG_JSON))
                .map(fileUpload -> {
                    try {
                        final var fileContent = Files.readString(fileUpload.filePath());
                        final var configModel = objectMapper.readValue(fileContent, VersionConfigModel.class);
                        return configModel;
                    } catch (IOException e) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.CONFIG_JSON_WRONG,
                                String.format("config.json was not parsed, request=%s, %s",
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
                                String.format("config.json was not parsed, request=%s, %s",
                                        request, e.getMessage()), e);
                    }
                })
                .findFirst()
                .orElseThrow(() -> new ServerSideBadRequestException(ExceptionQualifierEnum.PROJECT_ZIP_NOT_FOUND,
                        PROJECT_ZIP + " was not found, request=" + request));
    }
}

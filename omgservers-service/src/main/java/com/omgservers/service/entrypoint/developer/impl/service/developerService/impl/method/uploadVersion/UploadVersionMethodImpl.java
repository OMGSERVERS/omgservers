package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.uploadVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.UploadVersionDeveloperRequest;
import com.omgservers.model.dto.developer.UploadVersionDeveloperResponse;
import com.omgservers.model.file.DecodedFileModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.service.entrypoint.developer.impl.operation.EncodeFilesOperation;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createVersion.CreateVersionMethod;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UploadVersionMethodImpl implements UploadVersionMethod {

    private static final String CONFIG_JSON = "config.json";
    private static final String LUA_EXTENSION = ".lua";

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

        final var sourceCode = getSourceCode(request);
        createVersionDeveloperRequest.setSourceCode(sourceCode);

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
                        "config.json was not found, request=" + request));
    }

    VersionSourceCodeModel getSourceCode(final UploadVersionDeveloperRequest request) {
        final var decodedFiles = request.getFiles().stream()
                .filter(fileUpload -> fileUpload.name().endsWith(LUA_EXTENSION))
                .map(fileUpload -> {
                    try {
                        final var fileContent = Files.readString(fileUpload.filePath());
                        return new DecodedFileModel(fileUpload.name(), fileContent);
                    } catch (IOException e) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.VERSION_FILE_WRONG,
                                String.format("%s was not parsed, request=%s, %s", fileUpload.fileName(), request,
                                        e.getMessage()), e);
                    }
                })
                .toList();
        final var encodedFiles = encodeFilesOperation.encodeFiles(decodedFiles);
        return new VersionSourceCodeModel(encodedFiles);
    }
}

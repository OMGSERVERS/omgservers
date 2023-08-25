package com.omgservers.application.module.versionModule.impl.operation.getVersionConfigOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionStageConfigModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetVersionConfigOperationImpl implements GetVersionConfigOperation {
    static final String CONFIG_FILE_NAME = "config.json";

    final ObjectMapper objectMapper;

    @Override
    public VersionStageConfigModel getVersionConfig(VersionModel version) {
        final var configJsonOptional = version.getSourceCode().getFiles().stream()
                .filter(file -> file.getFileName().equals(CONFIG_FILE_NAME))
                .findFirst();
        if (configJsonOptional.isEmpty()) {
            throw new ServerSideConflictException("config.json was not found");
        }

        final var configJsonFile = configJsonOptional.get();
        final var configJsonBytes = Base64.getDecoder().decode(configJsonFile.getBase64content());
        final var configJsonString = new String(configJsonBytes);
        try {
            final var versionConfig = objectMapper.readValue(configJsonString, VersionStageConfigModel.class);
            return versionConfig;
        } catch (IOException e) {
            throw new ServerSideConflictException("config.json is wrong, " + e.getMessage());
        }
    }
}

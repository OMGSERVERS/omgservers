package com.omgservers.application.module.developerModule.impl.service.developerHelpService.request;

import com.omgservers.application.module.versionModule.model.VersionSourceCodeModel;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVersionHelpRequest {

    static public void validate(CreateVersionHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    UUID tenant;
    UUID stage;
    VersionStageConfigModel stageConfig;
    VersionSourceCodeModel sourceCode;
}

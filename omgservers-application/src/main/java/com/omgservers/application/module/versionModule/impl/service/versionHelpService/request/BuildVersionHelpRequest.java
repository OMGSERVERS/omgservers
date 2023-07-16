package com.omgservers.application.module.versionModule.impl.service.versionHelpService.request;

import com.omgservers.application.module.versionModule.model.VersionSourceCodeModel;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildVersionHelpRequest {

    static public void validate(BuildVersionHelpRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
        // TODO: validate fields
    }

    UUID tenant;
    UUID stage;
    VersionStageConfigModel stageConfig;
    VersionSourceCodeModel sourceCode;
}

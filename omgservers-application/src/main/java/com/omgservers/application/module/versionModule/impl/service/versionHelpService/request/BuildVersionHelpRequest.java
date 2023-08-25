package com.omgservers.application.module.versionModule.impl.service.versionHelpService.request;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    Long tenantId;
    Long stageId;
    VersionStageConfigModel stageConfig;
    VersionSourceCodeModel sourceCode;
}

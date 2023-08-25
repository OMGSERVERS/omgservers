package com.omgservers.dto.developerModule;

import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVersionDeveloperRequest {

    static public void validate(CreateVersionDeveloperRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long tenantId;
    Long stageId;
    VersionStageConfigModel stageConfig;
    VersionSourceCodeModel sourceCode;
}

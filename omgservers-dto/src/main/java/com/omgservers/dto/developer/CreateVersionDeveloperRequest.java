package com.omgservers.dto.developer;

import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionConfigModel;
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
    VersionConfigModel versionConfig;
    VersionSourceCodeModel sourceCode;
}

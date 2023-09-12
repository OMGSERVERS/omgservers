package com.omgservers.dto.tenant;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildVersionRequest {

    public static void validate(BuildVersionRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
        // TODO: validate fields
    }

    Long tenantId;
    Long stageId;
    VersionConfigModel versionConfig;
    VersionSourceCodeModel sourceCode;
}

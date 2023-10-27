package com.omgservers.dto.tenant;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildVersionRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    VersionConfigModel versionConfig;

    @NotNull
    VersionSourceCodeModel sourceCode;
}

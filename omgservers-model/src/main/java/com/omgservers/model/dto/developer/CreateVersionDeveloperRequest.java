package com.omgservers.model.dto.developer;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVersionDeveloperRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotNull
    VersionConfigModel versionConfig;

    @NotNull
    VersionSourceCodeModel sourceCode;
}

package com.omgservers.model.dto.developer;

import com.omgservers.model.version.VersionConfigModel;
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
    String base64Archive;
}

package com.omgservers.dto.versionModule;

import com.omgservers.model.version.VersionStageConfigModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStageConfigInternalResponse {

    VersionStageConfigModel stageConfig;
}

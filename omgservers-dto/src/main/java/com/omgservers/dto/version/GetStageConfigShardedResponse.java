package com.omgservers.dto.version;

import com.omgservers.model.version.VersionStageConfigModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStageConfigShardedResponse {

    VersionStageConfigModel stageConfig;
}

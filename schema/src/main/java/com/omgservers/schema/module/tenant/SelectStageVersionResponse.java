package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.version.VersionProjectionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectStageVersionResponse {

    VersionProjectionModel versionProjection;
}

package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.version.VersionConfigModel;
import com.omgservers.schema.model.version.VersionConfigModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionConfigResponse {

    VersionConfigModel versionConfig;
}

package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.version.VersionConfigDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionConfigResponse {

    VersionConfigDto versionConfig;
}

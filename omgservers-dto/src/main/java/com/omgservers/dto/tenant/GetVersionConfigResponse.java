package com.omgservers.dto.tenant;

import com.omgservers.model.version.VersionConfigModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionConfigResponse {

    VersionConfigModel versionConfig;
}

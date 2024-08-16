package com.omgservers.schema.entrypoint.runtime;

import com.omgservers.schema.model.version.VersionConfigDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetConfigRuntimeResponse {

    VersionConfigDto versionConfig;
}

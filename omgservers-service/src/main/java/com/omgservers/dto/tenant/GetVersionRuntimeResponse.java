package com.omgservers.dto.tenant;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionRuntimeResponse {

    VersionRuntimeModel versionRuntime;
}

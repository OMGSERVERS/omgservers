package com.omgservers.schema.module.tenant.version;

import com.omgservers.schema.module.tenant.version.dto.VersionDataDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionDataResponse {

    @NotNull
    VersionDataDto versionData;
}

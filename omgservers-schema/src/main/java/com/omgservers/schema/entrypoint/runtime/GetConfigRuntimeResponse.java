package com.omgservers.schema.entrypoint.runtime;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetConfigRuntimeResponse {

    TenantVersionConfigDto versionConfig;
}

package com.omgservers.schema.module.tenant.tenantBuildRequest;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantBuildRequestResponse {

    TenantBuildRequestModel tenantBuildRequest;
}

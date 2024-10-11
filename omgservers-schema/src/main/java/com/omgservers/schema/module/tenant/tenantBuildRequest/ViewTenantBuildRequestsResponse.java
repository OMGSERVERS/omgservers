package com.omgservers.schema.module.tenant.tenantBuildRequest;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantBuildRequestsResponse {

    List<TenantBuildRequestModel> tenantBuildRequests;
}

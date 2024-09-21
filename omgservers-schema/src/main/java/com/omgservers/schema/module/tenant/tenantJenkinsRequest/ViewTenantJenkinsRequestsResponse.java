package com.omgservers.schema.module.tenant.tenantJenkinsRequest;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantJenkinsRequestsResponse {

    List<TenantJenkinsRequestModel> versionJenkinsRequests;
}

package com.omgservers.schema.module.tenant.tenantMatchmakerRequest;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantMatchmakerRequestsResponse {

    List<TenantMatchmakerRequestModel> tenantMatchmakerRequests;
}

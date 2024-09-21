package com.omgservers.schema.module.tenant.tenantMatchmakerRequest;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantMatchmakerRequestResponse {

    TenantMatchmakerRequestModel tenantMatchmakerRequest;
}

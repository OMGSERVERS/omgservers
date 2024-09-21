package com.omgservers.schema.module.tenant.tenantLobbyRequest;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantLobbyRequestResponse {

    TenantLobbyRequestModel tenantLobbyRequest;
}

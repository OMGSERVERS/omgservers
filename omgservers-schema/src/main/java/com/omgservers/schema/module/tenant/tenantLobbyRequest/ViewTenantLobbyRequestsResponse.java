package com.omgservers.schema.module.tenant.tenantLobbyRequest;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantLobbyRequestsResponse {

    List<TenantLobbyRequestModel> tenantLobbyRequests;
}

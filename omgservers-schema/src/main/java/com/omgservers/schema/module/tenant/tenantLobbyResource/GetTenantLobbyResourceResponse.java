package com.omgservers.schema.module.tenant.tenantLobbyResource;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantLobbyResourceResponse {

    TenantLobbyResourceModel tenantLobbyResource;
}

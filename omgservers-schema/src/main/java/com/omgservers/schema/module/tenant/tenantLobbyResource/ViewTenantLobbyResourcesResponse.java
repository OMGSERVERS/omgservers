package com.omgservers.schema.module.tenant.tenantLobbyResource;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantLobbyResourcesResponse {

    List<TenantLobbyResourceModel> tenantLobbyResources;
}

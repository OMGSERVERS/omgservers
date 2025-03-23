package com.omgservers.schema.module.tenant.tenantMatchmakerResource;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantMatchmakerResourcesResponse {

    List<TenantMatchmakerResourceModel> tenantMatchmakerResources;
}

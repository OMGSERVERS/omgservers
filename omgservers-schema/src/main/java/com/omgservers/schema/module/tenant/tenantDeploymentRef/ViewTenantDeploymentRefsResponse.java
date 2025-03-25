package com.omgservers.schema.module.tenant.tenantDeploymentRef;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantDeploymentRefsResponse {

    List<TenantDeploymentRefModel> tenantDeploymentRefs;
}

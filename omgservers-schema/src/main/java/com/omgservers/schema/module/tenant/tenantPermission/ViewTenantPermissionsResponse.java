package com.omgservers.schema.module.tenant.tenantPermission;

import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantPermissionsResponse {

    List<TenantPermissionModel> tenantPermissions;
}

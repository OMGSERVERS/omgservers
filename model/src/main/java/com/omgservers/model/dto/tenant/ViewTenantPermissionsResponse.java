package com.omgservers.model.dto.tenant;

import com.omgservers.model.tenantPermission.TenantPermissionModel;
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

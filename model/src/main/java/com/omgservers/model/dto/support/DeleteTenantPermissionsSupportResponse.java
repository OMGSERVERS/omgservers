package com.omgservers.model.dto.support;

import com.omgservers.model.tenantPermission.TenantPermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTenantPermissionsSupportResponse {

    List<TenantPermissionEnum> deletedPermissions;
}

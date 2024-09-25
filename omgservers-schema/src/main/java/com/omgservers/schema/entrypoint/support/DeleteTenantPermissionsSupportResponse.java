package com.omgservers.schema.entrypoint.support;

import com.omgservers.schema.model.tenantPermission.TenantPermissionQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTenantPermissionsSupportResponse {

    List<TenantPermissionQualifierEnum> deletedPermissions;
}

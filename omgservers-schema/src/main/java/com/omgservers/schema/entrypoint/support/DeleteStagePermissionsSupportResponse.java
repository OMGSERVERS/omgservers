package com.omgservers.schema.entrypoint.support;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteStagePermissionsSupportResponse {

    List<TenantStagePermissionEnum> deletedPermissions;
}

package com.omgservers.schema.shard.tenant.tenantStagePermission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTenantStagePermissionExistsResponse {

    Boolean exists;
}

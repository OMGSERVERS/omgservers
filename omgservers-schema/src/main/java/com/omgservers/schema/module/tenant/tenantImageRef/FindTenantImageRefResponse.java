package com.omgservers.schema.module.tenant.tenantImageRef;

import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantImageRefResponse {

    TenantImageRefModel tenantImageRef;
}

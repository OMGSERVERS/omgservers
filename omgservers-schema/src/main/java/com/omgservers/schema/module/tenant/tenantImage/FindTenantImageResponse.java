package com.omgservers.schema.module.tenant.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantImageResponse {

    TenantImageModel tenantImage;
}

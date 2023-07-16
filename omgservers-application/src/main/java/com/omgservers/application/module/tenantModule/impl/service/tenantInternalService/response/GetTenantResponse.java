package com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response;

import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantResponse {

    TenantModel tenant;
}

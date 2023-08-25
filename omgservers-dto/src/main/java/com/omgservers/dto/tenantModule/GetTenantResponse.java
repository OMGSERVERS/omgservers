package com.omgservers.dto.tenantModule;

import com.omgservers.model.tenant.TenantModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantResponse {

    TenantModel tenant;
}

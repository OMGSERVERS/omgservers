package com.omgservers.schema.module.tenant.tenant;

import com.omgservers.schema.model.tenant.TenantModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantResponse {

    TenantModel tenant;
}
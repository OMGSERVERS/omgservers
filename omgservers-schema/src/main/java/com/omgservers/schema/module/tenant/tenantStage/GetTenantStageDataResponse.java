package com.omgservers.schema.module.tenant.tenantStage;

import com.omgservers.schema.module.tenant.tenantStage.dto.TenantStageDataDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantStageDataResponse {

    @NotNull
    TenantStageDataDto tenantStageData;
}

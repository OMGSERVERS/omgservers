package com.omgservers.schema.module.tenant.tenantProject;

import com.omgservers.schema.module.tenant.tenantProject.dto.TenantProjectDataDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantProjectDataResponse {

    @NotNull
    TenantProjectDataDto tenantProjectData;
}

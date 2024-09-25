package com.omgservers.schema.entrypoint.developer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantProjectDeveloperResponse {

    Long tenantProjectId;
    Long tenantStageId;
    String tenantStageSecret;
}

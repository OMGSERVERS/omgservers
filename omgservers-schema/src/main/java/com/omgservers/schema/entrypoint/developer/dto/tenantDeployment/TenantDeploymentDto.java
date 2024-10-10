package com.omgservers.schema.entrypoint.developer.dto.tenantDeployment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDeploymentDto {

    Long id;

    Long tenantId;

    Long stageId;

    Long versionId;

    Instant created;
}

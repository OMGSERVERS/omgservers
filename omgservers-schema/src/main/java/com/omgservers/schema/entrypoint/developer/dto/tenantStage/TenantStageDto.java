package com.omgservers.schema.entrypoint.developer.dto.tenantStage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantStageDto {

    Long id;

    Instant created;
}

package com.omgservers.schema.entrypoint.developer.dto.tenantProject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantVersionProjectionDto {

    Long id;

    Instant created;
}

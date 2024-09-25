package com.omgservers.schema.entrypoint.developer.dto.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDto {

    Long id;

    Instant created;
}

package com.omgservers.schema.entrypoint.developer.dto.tenantProject;

import com.omgservers.schema.entrypoint.developer.dto.alias.AliasDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantProjectDto {

    Long id;

    Long tenantId;

    Instant created;

    List<AliasDto> aliases;
}

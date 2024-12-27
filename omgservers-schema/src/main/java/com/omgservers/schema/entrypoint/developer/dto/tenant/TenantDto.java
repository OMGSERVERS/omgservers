package com.omgservers.schema.entrypoint.developer.dto.tenant;

import com.omgservers.schema.entrypoint.developer.dto.alias.AliasDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDto {

    Long id;

    Instant created;

    List<AliasDto> aliases;
}

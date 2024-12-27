package com.omgservers.schema.entrypoint.developer.dto.tenantStage;

import com.omgservers.schema.entrypoint.developer.dto.alias.AliasDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantStageDto {

    Long id;

    Long tenantId;

    Long projectId;

    Instant created;

    List<AliasDto> aliases;
}

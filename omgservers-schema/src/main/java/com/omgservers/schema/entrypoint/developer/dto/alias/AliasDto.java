package com.omgservers.schema.entrypoint.developer.dto.alias;

import com.omgservers.schema.model.alias.AliasQualifierEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliasDto {

    Long id;

    Instant created;

    AliasQualifierEnum qualifier;

    Long entityId;

    String value;
}

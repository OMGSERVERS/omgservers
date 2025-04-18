package com.omgservers.schema.shard.alias;

import com.omgservers.schema.model.alias.AliasModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAliasResponse {

    AliasModel alias;
}

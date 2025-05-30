package com.omgservers.schema.shard.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncAliasRequest implements ShardRequest {

    @NotNull
    AliasModel alias;

    @Override
    public String getRequestShardKey() {
        return alias.getShardKey();
    }
}

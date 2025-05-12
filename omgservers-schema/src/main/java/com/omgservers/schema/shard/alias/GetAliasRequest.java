package com.omgservers.schema.shard.alias;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAliasRequest implements ShardRequest {

    @NotNull
    String shardKey;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return shardKey;
    }
}

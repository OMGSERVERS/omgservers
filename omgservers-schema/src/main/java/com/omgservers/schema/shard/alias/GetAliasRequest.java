package com.omgservers.schema.shard.alias;

import com.omgservers.schema.shard.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAliasRequest implements ShardedRequest {

    @NotNull
    Long shardKey;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return shardKey.toString();
    }
}

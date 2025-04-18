package com.omgservers.schema.shard.alias;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAliasRequest implements ShardRequest {

    @NotNull
    Long shardKey;

    @NotNull
    Long uniquenessGroup;

    @NotNull
    String value;

    @Override
    public String getRequestShardKey() {
        return shardKey.toString();
    }
}

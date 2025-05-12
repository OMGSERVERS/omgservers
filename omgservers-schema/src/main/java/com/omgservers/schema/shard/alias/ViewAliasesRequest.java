package com.omgservers.schema.shard.alias;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewAliasesRequest implements ShardRequest {

    @NotNull
    String shardKey;

    Long uniquenessGroup;

    Long entityId;

    @Override
    public String getRequestShardKey() {
        return shardKey;
    }
}

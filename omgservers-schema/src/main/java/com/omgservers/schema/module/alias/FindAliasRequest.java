package com.omgservers.schema.module.alias;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAliasRequest implements ShardedRequest {

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

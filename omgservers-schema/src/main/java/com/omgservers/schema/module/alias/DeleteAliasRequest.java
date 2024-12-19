package com.omgservers.schema.module.alias;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAliasRequest implements ShardedRequest {

    @NotNull
    Long shardKey;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return shardKey.toString();
    }
}

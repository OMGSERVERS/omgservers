package com.omgservers.schema.shard.root.rootEntityRef;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindRootEntityRefRequest implements ShardRequest {

    @NotNull
    Long rootId;

    @NotNull
    Long entityId;

    @Override
    public String getRequestShardKey() {
        return rootId.toString();
    }
}

package com.omgservers.schema.shard.root.rootEntityRef;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRootEntityRefRequest implements ShardRequest {

    @NotNull
    RootEntityRefModel rootEntityRef;

    @Override
    public String getRequestShardKey() {
        return rootEntityRef.getRootId().toString();
    }
}

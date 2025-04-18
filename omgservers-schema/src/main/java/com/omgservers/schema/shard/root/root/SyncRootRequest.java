package com.omgservers.schema.shard.root.root;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.root.RootModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRootRequest implements ShardRequest {

    @NotNull
    RootModel root;

    @Override
    public String getRequestShardKey() {
        return root.getId().toString();
    }
}

package com.omgservers.schema.module.root.rootEntityRef;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRootEntityRefRequest implements ShardedRequest {

    @NotNull
    RootEntityRefModel rootEntityRef;

    @Override
    public String getRequestShardKey() {
        return rootEntityRef.getRootId().toString();
    }
}

package com.omgservers.schema.module.root.root;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.root.RootModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.root.RootModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRootRequest implements ShardedRequest {

    @NotNull
    RootModel root;

    @Override
    public String getRequestShardKey() {
        return root.getId().toString();
    }
}

package com.omgservers.model.dto.root.rootEntityRef;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.rootEntityRef.RootEntityRefModel;
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

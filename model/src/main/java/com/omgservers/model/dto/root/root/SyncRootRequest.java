package com.omgservers.model.dto.root.root;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.root.RootModel;
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

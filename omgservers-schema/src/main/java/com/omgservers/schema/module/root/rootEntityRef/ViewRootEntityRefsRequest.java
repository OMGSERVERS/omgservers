package com.omgservers.schema.module.root.rootEntityRef;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRootEntityRefsRequest implements ShardedRequest {

    @NotNull
    Long rootId;

    @Override
    public String getRequestShardKey() {
        return rootId.toString();
    }
}

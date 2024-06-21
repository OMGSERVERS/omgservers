package com.omgservers.model.dto.root.rootEntityRef;

import com.omgservers.model.dto.ShardedRequest;
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

package com.omgservers.model.dto.root.rootEntityRef;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRootEntityRefRequest implements ShardedRequest {

    @NotNull
    Long rootId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return rootId.toString();
    }
}

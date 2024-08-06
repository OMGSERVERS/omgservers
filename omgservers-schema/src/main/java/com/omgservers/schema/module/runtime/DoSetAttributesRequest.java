package com.omgservers.schema.module.runtime;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.player.PlayerAttributesModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.player.PlayerAttributesModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoSetAttributesRequest implements ShardedRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    Long clientId;

    @NotNull
    PlayerAttributesModel attributes;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}

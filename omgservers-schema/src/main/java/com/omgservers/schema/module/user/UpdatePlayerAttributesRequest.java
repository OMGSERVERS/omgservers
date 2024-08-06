package com.omgservers.schema.module.user;

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
public class UpdatePlayerAttributesRequest implements ShardedRequest {

    @NotNull
    Long userId;

    @NotNull
    Long playerId;

    @NotNull
    PlayerAttributesModel attributes;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}

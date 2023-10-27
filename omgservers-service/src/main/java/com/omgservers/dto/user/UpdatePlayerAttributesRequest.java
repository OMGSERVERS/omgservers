package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.player.PlayerAttributesModel;
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

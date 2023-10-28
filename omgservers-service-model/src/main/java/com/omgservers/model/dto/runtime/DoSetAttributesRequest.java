package com.omgservers.model.dto.runtime;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.player.PlayerAttributesModel;
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
    Long userId;

    @NotNull
    Long clientId;

    @NotNull
    PlayerAttributesModel attributes;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}

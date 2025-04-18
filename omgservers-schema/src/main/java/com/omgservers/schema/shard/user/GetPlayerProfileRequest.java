package com.omgservers.schema.shard.user;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerProfileRequest implements ShardRequest {

    @NotNull
    Long userId;

    @NotNull
    Long playerId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}

package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchmakerMatchClientsRequest implements ShardedRequest {

    @NotNull
    Long matchmakerId;

    @NotNull
    Long matchId;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}

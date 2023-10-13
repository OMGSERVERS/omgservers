package com.omgservers.dto.matchmaker;

import com.omgservers.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerStateRequest implements ShardedRequest {

    @NotNull
    Long matchmakerId;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
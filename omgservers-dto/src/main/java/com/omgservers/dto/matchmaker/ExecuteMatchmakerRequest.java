package com.omgservers.dto.matchmaker;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteMatchmakerRequest implements ShardedRequest {

    public static void validate(ExecuteMatchmakerRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long matchmakerId;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}

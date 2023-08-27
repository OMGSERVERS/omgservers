package com.omgservers.dto.matchmaker;

import com.omgservers.model.request.RequestModel;
import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRequestShardedRequest implements ShardedRequest {

    static public void validate(SyncRequestShardedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    RequestModel request;

    @Override
    public String getRequestShardKey() {
        return request.getMatchmakerId().toString();
    }
}

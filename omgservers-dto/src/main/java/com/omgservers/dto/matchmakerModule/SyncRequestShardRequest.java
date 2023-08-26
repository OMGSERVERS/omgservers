package com.omgservers.dto.matchmakerModule;

import com.omgservers.model.request.RequestModel;
import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRequestShardRequest implements ShardRequest {

    static public void validate(SyncRequestShardRequest request) {
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

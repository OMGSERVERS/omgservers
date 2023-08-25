package com.omgservers.dto.matchmakerModule;

import com.omgservers.model.request.RequestModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRequestInternalRequest implements InternalRequest {

    static public void validate(SyncRequestInternalRequest request) {
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

package com.omgservers.dto.matchmaker;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.request.RequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncRequestRequest implements ShardedRequest {

    @NotNull
    RequestModel request;

    @Override
    public String getRequestShardKey() {
        return request.getMatchmakerId().toString();
    }
}

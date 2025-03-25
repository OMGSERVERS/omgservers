package com.omgservers.schema.module.matchmaker.matchmakerCommand;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchmakerCommandsRequest implements ShardedRequest {

    @NotNull
    Long matchmakerId;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}

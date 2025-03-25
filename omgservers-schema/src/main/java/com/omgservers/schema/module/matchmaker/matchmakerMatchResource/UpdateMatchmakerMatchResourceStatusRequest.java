package com.omgservers.schema.module.matchmaker.matchmakerMatchResource;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMatchmakerMatchResourceStatusRequest implements ShardedRequest {

    @NotNull
    Long matchmakerId;

    @NotNull
    Long id;

    @NotNull
    MatchmakerMatchResourceStatusEnum status;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}

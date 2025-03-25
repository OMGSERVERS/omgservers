package com.omgservers.schema.module.matchmaker.matchmakerCommand;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerCommandRequest implements ShardedRequest {

    @NotNull
    MatchmakerCommandModel matchmakerCommand;

    @Override
    public String getRequestShardKey() {
        return matchmakerCommand.getMatchmakerId().toString();
    }
}

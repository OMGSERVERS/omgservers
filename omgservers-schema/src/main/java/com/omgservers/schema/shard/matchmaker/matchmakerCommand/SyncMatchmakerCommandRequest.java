package com.omgservers.schema.shard.matchmaker.matchmakerCommand;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerCommandRequest implements ShardRequest {

    @NotNull
    MatchmakerCommandModel matchmakerCommand;

    @Override
    public String getRequestShardKey() {
        return matchmakerCommand.getMatchmakerId().toString();
    }
}

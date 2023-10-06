package com.omgservers.dto.matchmaker;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
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

package com.omgservers.dto.matchmaker;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.matchCommand.MatchCommandModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchCommandRequest implements ShardedRequest {

    @NotNull
    MatchCommandModel matchCommand;

    @Override
    public String getRequestShardKey() {
        return matchCommand.getMatchmakerId().toString();
    }
}

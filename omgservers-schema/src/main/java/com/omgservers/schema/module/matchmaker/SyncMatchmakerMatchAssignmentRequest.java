package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerMatchAssignmentRequest implements ShardedRequest {

    @NotNull
    MatchmakerMatchAssignmentModel matchmakerMatchAssignment;

    @Override
    public String getRequestShardKey() {
        return matchmakerMatchAssignment.getMatchmakerId().toString();
    }
}

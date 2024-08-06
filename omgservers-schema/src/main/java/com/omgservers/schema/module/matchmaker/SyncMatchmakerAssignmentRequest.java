package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerAssignmentRequest implements ShardedRequest {

    @NotNull
    MatchmakerAssignmentModel matchmakerAssignment;

    @Override
    public String getRequestShardKey() {
        return matchmakerAssignment.getMatchmakerId().toString();
    }
}

package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.matchmakerAssignment.MatchmakerAssignmentModel;
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

package com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMatchmakerMatchAssignmentRequest implements ShardRequest {

    @NotNull
    Long matchmakerId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}

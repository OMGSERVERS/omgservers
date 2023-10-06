package com.omgservers.dto.matchmaker;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandStatusEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMatchmakerCommandsStatusRequest implements ShardedRequest {

    @NotNull
    Long matchmakerId;

    @NotNull
    @NotEmpty
    List<Long> ids;

    @NotNull
    MatchmakerCommandStatusEnum status;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}

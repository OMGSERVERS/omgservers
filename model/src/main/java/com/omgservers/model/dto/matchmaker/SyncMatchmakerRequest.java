package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.matchmaker.MatchmakerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerRequest implements ShardedRequest {

    @NotNull
    MatchmakerModel matchmaker;

    @Override
    public String getRequestShardKey() {
        return matchmaker.getId().toString();
    }
}

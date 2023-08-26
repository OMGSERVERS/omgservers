package com.omgservers.dto.matchmakerModule;

import com.omgservers.model.match.MatchModel;
import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchShardRequest implements ShardRequest {

    static public void validate(CreateMatchShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    MatchModel match;

    @Override
    public String getRequestShardKey() {
        return match.getMatchmakerId().toString();
    }
}

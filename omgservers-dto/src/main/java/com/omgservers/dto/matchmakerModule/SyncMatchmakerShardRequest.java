package com.omgservers.dto.matchmakerModule;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerShardRequest implements ShardRequest {

    static public void validate(SyncMatchmakerShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    MatchmakerModel matchmaker;

    @Override
    public String getRequestShardKey() {
        return matchmaker.getId().toString();
    }
}

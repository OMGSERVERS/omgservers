package com.omgservers.dto.matchmakerModule;

import com.omgservers.model.match.MatchModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchInternalRequest implements InternalRequest {

    static public void validate(CreateMatchInternalRequest request) {
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

package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request;

import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchInternalRequest implements InternalRequest {

    static public void validate(SyncMatchInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    MatchModel match;

    @Override
    public String getRequestShardKey() {
        return match.getMatchmaker().toString();
    }
}

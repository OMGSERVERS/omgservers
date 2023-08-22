package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request;

import com.omgservers.application.module.matchmakerModule.model.matchmaker.MatchmakerModel;
import com.omgservers.application.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerInternalRequest implements InternalRequest {

    static public void validate(SyncMatchmakerInternalRequest request) {
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

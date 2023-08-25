package com.omgservers.dto.matchmakerModule;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.dto.InternalRequest;
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

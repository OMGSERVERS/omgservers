package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request;

import com.omgservers.application.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoMatchmakingInternalRequest implements InternalRequest {

    static public void validate(DoMatchmakingInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long matchmakerId;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}

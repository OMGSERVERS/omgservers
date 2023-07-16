package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request;

import com.omgservers.application.request.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchInternalRequest implements InternalRequest {

    static public void validate(GetMatchInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UUID matchmaker;
    UUID uuid;

    @Override
    public String getRequestShardKey() {
        return matchmaker.toString();
    }
}

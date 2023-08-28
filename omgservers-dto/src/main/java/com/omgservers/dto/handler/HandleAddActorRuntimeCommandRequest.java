package com.omgservers.dto.handler;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleAddActorRuntimeCommandRequest {

    static public void validate(final HandleAddActorRuntimeCommandRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
        // TODO: validate fields
    }

    Long runtimeId;
    Long userId;
    Long playerId;
    Long clientId;
}

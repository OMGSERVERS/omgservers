package com.omgservers.dto.context;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleHandleIncomingRuntimeCommandRequest {

    static public void validate(final HandleHandleIncomingRuntimeCommandRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
        // TODO: validate fields
    }

    Long runtimeId;
    Long userId;
    Long playerId;
    Long clientId;
    String incoming;
}

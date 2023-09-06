package com.omgservers.module.gateway.impl.service.connectionService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAssignedPlayerRequest {

    public static void validate(GetAssignedPlayerRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long connectionId;
}

package com.omgservers.module.user.impl.service.playerService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrCreatePlayerHelpRequest {

    public static void validate(GetOrCreatePlayerHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long userId;
    Long stageId;
}

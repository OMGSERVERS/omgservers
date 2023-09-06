package com.omgservers.module.gateway.impl.service.connectionService.request;

import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignPlayerRequest {

    public static void validate(AssignPlayerRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long connectionId;
    AssignedPlayerModel assignedPlayer;
}

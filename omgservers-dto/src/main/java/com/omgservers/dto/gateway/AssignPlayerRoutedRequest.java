package com.omgservers.dto.gateway;

import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignPlayerRoutedRequest {

    static public void validate(AssignPlayerRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    URI server;
    Long connectionId;
    AssignedPlayerModel assignedPlayer;
}

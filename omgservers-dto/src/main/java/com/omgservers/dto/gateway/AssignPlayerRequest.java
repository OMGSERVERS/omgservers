package com.omgservers.dto.gateway;

import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignPlayerRequest {

    @NotNull
    URI server;

    @NotNull
    Long connectionId;

    @NotNull
    AssignedPlayerModel assignedPlayer;
}

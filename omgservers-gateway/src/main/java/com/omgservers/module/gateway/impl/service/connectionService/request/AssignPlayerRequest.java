package com.omgservers.module.gateway.impl.service.connectionService.request;

import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignPlayerRequest {

    @NotNull
    Long connectionId;

    @NotNull
    AssignedPlayerModel assignedPlayer;
}

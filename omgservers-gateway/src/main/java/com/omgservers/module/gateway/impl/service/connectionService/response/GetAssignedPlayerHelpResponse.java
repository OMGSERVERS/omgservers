package com.omgservers.module.gateway.impl.service.connectionService.response;

import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAssignedPlayerHelpResponse {

    AssignedPlayerModel assignedPlayer;
}

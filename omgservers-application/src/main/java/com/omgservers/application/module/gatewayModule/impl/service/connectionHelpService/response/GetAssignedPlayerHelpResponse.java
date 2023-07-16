package com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response;

import com.omgservers.application.module.gatewayModule.model.assignedPlayer.AssignedPlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAssignedPlayerHelpResponse {

    AssignedPlayerModel assignedPlayer;
}

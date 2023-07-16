package com.omgservers.application.module.userModule.impl.service.playerHelpService.response;

import com.omgservers.application.module.userModule.model.player.PlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrCreatePlayerHelpResponse {

    Boolean created;
    PlayerModel player;
}

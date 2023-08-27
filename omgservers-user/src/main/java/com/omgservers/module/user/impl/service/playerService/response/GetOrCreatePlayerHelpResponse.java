package com.omgservers.module.user.impl.service.playerService.response;

import com.omgservers.model.player.PlayerModel;
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

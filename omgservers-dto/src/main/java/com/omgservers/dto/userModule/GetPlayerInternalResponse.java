package com.omgservers.dto.userModule;

import com.omgservers.model.player.PlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerInternalResponse {

    PlayerModel player;
}

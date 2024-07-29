package com.omgservers.schema.module.user;

import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.player.PlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPlayerResponse {

    PlayerModel player;
}

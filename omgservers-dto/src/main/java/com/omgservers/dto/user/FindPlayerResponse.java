package com.omgservers.dto.user;

import com.omgservers.model.player.PlayerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPlayerResponse {

    PlayerModel player;
}

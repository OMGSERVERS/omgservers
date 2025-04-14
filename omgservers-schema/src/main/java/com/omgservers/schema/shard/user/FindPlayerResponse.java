package com.omgservers.schema.shard.user;

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

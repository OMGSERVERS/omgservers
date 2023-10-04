package com.omgservers.dto.user;

import com.omgservers.model.player.PlayerObjectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerObjectResponse {

    PlayerObjectModel object;
}

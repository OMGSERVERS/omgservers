package com.omgservers.dto.user;

import com.omgservers.model.player.PlayerAttributesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerAttributesResponse {

    PlayerAttributesModel attributes;
}

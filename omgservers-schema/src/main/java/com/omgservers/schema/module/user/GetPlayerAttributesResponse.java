package com.omgservers.schema.module.user;

import com.omgservers.schema.model.player.PlayerAttributesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerAttributesResponse {

    PlayerAttributesDto attributes;
}

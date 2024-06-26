package com.omgservers.service.module.user.impl.service.userService.impl.method.player.getPlayerAttributes;

import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerAttributesMethod {
    Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request);
}

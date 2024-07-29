package com.omgservers.service.module.user.impl.service.userService.impl.method.player.getPlayerAttributes;

import com.omgservers.schema.module.user.GetPlayerAttributesRequest;
import com.omgservers.schema.module.user.GetPlayerAttributesResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerAttributesMethod {
    Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request);
}

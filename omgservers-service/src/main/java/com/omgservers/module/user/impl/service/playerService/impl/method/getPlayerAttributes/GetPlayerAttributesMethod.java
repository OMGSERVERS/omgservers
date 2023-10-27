package com.omgservers.module.user.impl.service.playerService.impl.method.getPlayerAttributes;

import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerAttributesMethod {
    Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request);
}

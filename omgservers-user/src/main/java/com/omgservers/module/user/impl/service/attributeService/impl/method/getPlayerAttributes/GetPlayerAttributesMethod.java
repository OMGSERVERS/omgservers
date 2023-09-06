package com.omgservers.module.user.impl.service.attributeService.impl.method.getPlayerAttributes;

import com.omgservers.dto.user.GetPlayerAttributesShardedResponse;
import com.omgservers.dto.user.GetPlayerAttributesShardedRequest;
import io.smallrye.mutiny.Uni;

public interface GetPlayerAttributesMethod {
    Uni<GetPlayerAttributesShardedResponse> getPlayerAttributes(GetPlayerAttributesShardedRequest request);
}

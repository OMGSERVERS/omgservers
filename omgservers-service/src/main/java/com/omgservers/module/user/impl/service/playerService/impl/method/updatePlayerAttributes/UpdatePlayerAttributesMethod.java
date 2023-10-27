package com.omgservers.module.user.impl.service.playerService.impl.method.updatePlayerAttributes;

import com.omgservers.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.dto.user.UpdatePlayerAttributesResponse;
import io.smallrye.mutiny.Uni;

public interface UpdatePlayerAttributesMethod {
    Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(UpdatePlayerAttributesRequest request);
}

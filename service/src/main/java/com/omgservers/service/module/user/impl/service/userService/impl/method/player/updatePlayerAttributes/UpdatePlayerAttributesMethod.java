package com.omgservers.service.module.user.impl.service.userService.impl.method.player.updatePlayerAttributes;

import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import io.smallrye.mutiny.Uni;

public interface UpdatePlayerAttributesMethod {
    Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(UpdatePlayerAttributesRequest request);
}

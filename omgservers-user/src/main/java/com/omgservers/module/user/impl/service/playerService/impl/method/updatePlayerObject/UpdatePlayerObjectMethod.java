package com.omgservers.module.user.impl.service.playerService.impl.method.updatePlayerObject;

import com.omgservers.dto.user.UpdatePlayerObjectRequest;
import com.omgservers.dto.user.UpdatePlayerObjectResponse;
import io.smallrye.mutiny.Uni;

public interface UpdatePlayerObjectMethod {
    Uni<UpdatePlayerObjectResponse> updatePlayerObject(UpdatePlayerObjectRequest request);
}

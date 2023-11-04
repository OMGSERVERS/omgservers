package com.omgservers.service.module.user.impl.service.playerService.impl.method.updatePlayerObject;

import com.omgservers.model.dto.user.UpdatePlayerObjectRequest;
import com.omgservers.model.dto.user.UpdatePlayerObjectResponse;
import io.smallrye.mutiny.Uni;

public interface UpdatePlayerObjectMethod {
    Uni<UpdatePlayerObjectResponse> updatePlayerObject(UpdatePlayerObjectRequest request);
}

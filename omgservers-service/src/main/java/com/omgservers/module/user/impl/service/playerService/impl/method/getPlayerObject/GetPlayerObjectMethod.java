package com.omgservers.module.user.impl.service.playerService.impl.method.getPlayerObject;

import com.omgservers.model.dto.user.GetPlayerObjectRequest;
import com.omgservers.model.dto.user.GetPlayerObjectResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerObjectMethod {
    Uni<GetPlayerObjectResponse> getPlayerObject(GetPlayerObjectRequest request);
}

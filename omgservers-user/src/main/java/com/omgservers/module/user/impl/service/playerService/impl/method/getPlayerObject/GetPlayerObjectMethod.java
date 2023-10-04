package com.omgservers.module.user.impl.service.playerService.impl.method.getPlayerObject;

import com.omgservers.dto.user.GetPlayerObjectRequest;
import com.omgservers.dto.user.GetPlayerObjectResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerObjectMethod {
    Uni<GetPlayerObjectResponse> getPlayerObject(GetPlayerObjectRequest request);
}

package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.getPlayerMethod;

import com.omgservers.dto.userModule.GetPlayerInternalRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerMethod {
    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerInternalRequest request);
}

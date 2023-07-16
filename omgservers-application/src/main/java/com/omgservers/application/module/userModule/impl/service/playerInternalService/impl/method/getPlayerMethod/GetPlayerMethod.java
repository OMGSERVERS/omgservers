package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.getPlayerMethod;

import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.GetPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.GetPlayerInternalResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerMethod {
    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerInternalRequest request);
}

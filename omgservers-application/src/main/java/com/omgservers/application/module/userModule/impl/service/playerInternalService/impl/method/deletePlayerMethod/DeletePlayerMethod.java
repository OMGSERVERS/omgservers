package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.deletePlayerMethod;

import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.DeletePlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.DeletePlayerInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePlayerMethod {
    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerInternalRequest request);
}

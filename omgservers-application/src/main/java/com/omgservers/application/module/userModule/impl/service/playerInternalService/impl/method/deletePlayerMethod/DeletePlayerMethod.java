package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.deletePlayerMethod;

import com.omgservers.dto.userModule.DeletePlayerInternalRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePlayerMethod {
    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerInternalRequest request);
}

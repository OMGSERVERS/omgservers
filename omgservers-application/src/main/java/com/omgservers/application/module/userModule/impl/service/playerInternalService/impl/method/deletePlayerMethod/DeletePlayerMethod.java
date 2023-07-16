package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.deletePlayerMethod;

import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.DeletePlayerInternalRequest;
import io.smallrye.mutiny.Uni;

public interface DeletePlayerMethod {
    Uni<Void> deletePlayer(DeletePlayerInternalRequest request);
}

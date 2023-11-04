package com.omgservers.service.module.user.impl.service.playerService.impl.method.deletePlayer;

import com.omgservers.model.dto.user.DeletePlayerResponse;
import com.omgservers.model.dto.user.DeletePlayerRequest;
import io.smallrye.mutiny.Uni;

public interface DeletePlayerMethod {
    Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request);
}

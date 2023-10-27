package com.omgservers.module.user.impl.service.playerService.impl.method.deletePlayer;

import com.omgservers.dto.user.DeletePlayerResponse;
import com.omgservers.dto.user.DeletePlayerRequest;
import io.smallrye.mutiny.Uni;

public interface DeletePlayerMethod {
    Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request);
}

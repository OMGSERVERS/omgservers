package com.omgservers.service.module.user.impl.service.userService.impl.method.player.deletePlayer;

import com.omgservers.schema.module.user.DeletePlayerResponse;
import com.omgservers.schema.module.user.DeletePlayerRequest;
import io.smallrye.mutiny.Uni;

public interface DeletePlayerMethod {
    Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request);
}

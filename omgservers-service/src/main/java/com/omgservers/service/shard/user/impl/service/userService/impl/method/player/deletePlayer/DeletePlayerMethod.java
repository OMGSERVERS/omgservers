package com.omgservers.service.shard.user.impl.service.userService.impl.method.player.deletePlayer;

import com.omgservers.schema.module.user.DeletePlayerRequest;
import com.omgservers.schema.module.user.DeletePlayerResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePlayerMethod {
    Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request);
}

package com.omgservers.service.shard.user.impl.service.userService.impl.method.player.findPlayer;

import com.omgservers.schema.module.user.FindPlayerRequest;
import com.omgservers.schema.module.user.FindPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface FindPlayerMethod {
    Uni<FindPlayerResponse> findPlayer(FindPlayerRequest request);
}

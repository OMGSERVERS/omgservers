package com.omgservers.service.module.user.impl.service.userService.impl.method.player.getPlayer;

import com.omgservers.schema.module.user.GetPlayerRequest;
import com.omgservers.schema.module.user.GetPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface GetPlayerMethod {
    Uni<GetPlayerResponse> getPlayer(GetPlayerRequest request);
}

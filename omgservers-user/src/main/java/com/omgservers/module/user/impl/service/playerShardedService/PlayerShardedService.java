package com.omgservers.module.user.impl.service.playerShardedService;

import com.omgservers.dto.user.DeletePlayerShardedResponse;
import com.omgservers.dto.user.DeletePlayerShardedRequest;
import com.omgservers.dto.user.GetPlayerShardedResponse;
import com.omgservers.dto.user.GetPlayerShardedRequest;
import com.omgservers.dto.user.SyncPlayerShardedResponse;
import com.omgservers.dto.user.SyncPlayerShardedRequest;
import io.smallrye.mutiny.Uni;

public interface PlayerShardedService {

    Uni<GetPlayerShardedResponse> getPlayer(GetPlayerShardedRequest request);

    Uni<SyncPlayerShardedResponse> syncPlayer(SyncPlayerShardedRequest request);

    Uni<DeletePlayerShardedResponse> deletePlayer(DeletePlayerShardedRequest request);
}

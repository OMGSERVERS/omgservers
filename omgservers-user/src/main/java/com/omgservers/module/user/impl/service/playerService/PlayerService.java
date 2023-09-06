package com.omgservers.module.user.impl.service.playerService;

import com.omgservers.dto.user.DeletePlayerShardedResponse;
import com.omgservers.dto.user.DeletePlayerShardedRequest;
import com.omgservers.dto.user.GetOrCreatePlayerHelpRequest;
import com.omgservers.dto.user.GetOrCreatePlayerHelpResponse;
import com.omgservers.dto.user.GetPlayerShardedResponse;
import com.omgservers.dto.user.GetPlayerShardedRequest;
import com.omgservers.dto.user.SyncPlayerShardedResponse;
import com.omgservers.dto.user.SyncPlayerShardedRequest;
import io.smallrye.mutiny.Uni;

public interface PlayerService {

    Uni<GetPlayerShardedResponse> getPlayer(GetPlayerShardedRequest request);

    Uni<SyncPlayerShardedResponse> syncPlayer(SyncPlayerShardedRequest request);

    Uni<DeletePlayerShardedResponse> deletePlayer(DeletePlayerShardedRequest request);

    Uni<GetOrCreatePlayerHelpResponse> getOrCreatePlayer(GetOrCreatePlayerHelpRequest request);
}

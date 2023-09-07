package com.omgservers.module.user.impl.service.playerService;

import com.omgservers.dto.user.DeletePlayerResponse;
import com.omgservers.dto.user.DeletePlayerRequest;
import com.omgservers.dto.user.GetOrCreatePlayerRequest;
import com.omgservers.dto.user.GetOrCreatePlayerResponse;
import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.SyncPlayerResponse;
import com.omgservers.dto.user.SyncPlayerRequest;
import io.smallrye.mutiny.Uni;

public interface PlayerService {

    Uni<GetPlayerResponse> getPlayer(GetPlayerRequest request);

    Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request);

    Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request);

    Uni<GetOrCreatePlayerResponse> getOrCreatePlayer(GetOrCreatePlayerRequest request);
}

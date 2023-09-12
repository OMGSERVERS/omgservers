package com.omgservers.module.user.impl.service.playerService;

import com.omgservers.dto.user.DeletePlayerRequest;
import com.omgservers.dto.user.DeletePlayerResponse;
import com.omgservers.dto.user.FindPlayerRequest;
import com.omgservers.dto.user.FindPlayerResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.dto.user.SyncPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface PlayerService {

    Uni<GetPlayerResponse> getPlayer(GetPlayerRequest request);

    Uni<FindPlayerResponse> findPlayer(FindPlayerRequest request);

    Uni<SyncPlayerResponse> syncPlayer(SyncPlayerRequest request);

    Uni<DeletePlayerResponse> deletePlayer(DeletePlayerRequest request);
}

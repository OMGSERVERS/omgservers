package com.omgservers.application.module.userModule.impl.service.playerInternalService;

import com.omgservers.dto.userModule.DeletePlayerShardRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetPlayerShardRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerShardRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import io.smallrye.mutiny.Uni;

public interface PlayerInternalService {

    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerShardRequest request);

    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerShardRequest request);

    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerShardRequest request);
}

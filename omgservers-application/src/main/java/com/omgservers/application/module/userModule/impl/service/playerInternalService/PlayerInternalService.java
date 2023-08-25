package com.omgservers.application.module.userModule.impl.service.playerInternalService;

import com.omgservers.dto.userModule.DeletePlayerInternalRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetPlayerInternalRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerInternalRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import io.smallrye.mutiny.Uni;

public interface PlayerInternalService {

    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerInternalRequest request);

    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerInternalRequest request);

    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerInternalRequest request);
}

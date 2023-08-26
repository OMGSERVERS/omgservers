package com.omgservers.application.module.userModule.impl.service.playerInternalService;

import com.omgservers.dto.userModule.DeletePlayerRoutedRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetPlayerRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerRoutedRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import io.smallrye.mutiny.Uni;

public interface PlayerInternalService {

    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerRoutedRequest request);

    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerRoutedRequest request);

    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerRoutedRequest request);
}

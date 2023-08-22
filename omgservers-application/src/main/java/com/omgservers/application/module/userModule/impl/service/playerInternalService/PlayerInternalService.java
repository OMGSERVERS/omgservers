package com.omgservers.application.module.userModule.impl.service.playerInternalService;

import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.DeletePlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.GetPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.SyncPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.DeletePlayerInternalResponse;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.GetPlayerInternalResponse;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.SyncPlayerInternalResponse;
import io.smallrye.mutiny.Uni;

public interface PlayerInternalService {

    Uni<GetPlayerInternalResponse> getPlayer(GetPlayerInternalRequest request);

    Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerInternalRequest request);

    Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerInternalRequest request);
}

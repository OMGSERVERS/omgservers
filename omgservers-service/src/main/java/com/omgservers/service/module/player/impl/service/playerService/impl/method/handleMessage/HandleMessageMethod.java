package com.omgservers.service.module.player.impl.service.playerService.impl.method.handleMessage;

import com.omgservers.model.dto.player.HandleMessagePlayerRequest;
import com.omgservers.model.dto.player.HandleMessagePlayerResponse;
import io.smallrye.mutiny.Uni;

public interface HandleMessageMethod {
    Uni<HandleMessagePlayerResponse> handleMessage(HandleMessagePlayerRequest request);
}

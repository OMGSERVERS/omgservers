package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.interchangeMethod;

import com.omgservers.model.dto.player.InterchangePlayerRequest;
import com.omgservers.model.dto.player.InterchangePlayerResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMethod {
    Uni<InterchangePlayerResponse> interchange(InterchangePlayerRequest request);
}

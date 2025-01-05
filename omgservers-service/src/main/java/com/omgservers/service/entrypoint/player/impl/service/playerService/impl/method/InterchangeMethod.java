package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method;

import com.omgservers.schema.entrypoint.player.InterchangePlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangePlayerResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMethod {
    Uni<InterchangePlayerResponse> execute(InterchangePlayerRequest request);
}

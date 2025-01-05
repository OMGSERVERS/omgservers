package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method;

import com.omgservers.schema.entrypoint.player.PingServicePlayerRequest;
import com.omgservers.schema.entrypoint.player.PingServicePlayerResponse;
import io.smallrye.mutiny.Uni;

public interface PingServiceMethod {
    Uni<PingServicePlayerResponse> execute(PingServicePlayerRequest request);
}

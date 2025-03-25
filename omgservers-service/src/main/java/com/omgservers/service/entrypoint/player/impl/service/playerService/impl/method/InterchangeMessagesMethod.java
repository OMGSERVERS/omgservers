package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method;

import com.omgservers.schema.entrypoint.player.InterchangeMessagesPlayerRequest;
import com.omgservers.schema.entrypoint.player.InterchangeMessagesPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMessagesMethod {
    Uni<InterchangeMessagesPlayerResponse> execute(InterchangeMessagesPlayerRequest request);
}

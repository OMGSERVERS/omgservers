package com.omgservers.service.module.player.impl.service.playerService.impl.method.receiveMessages;

import com.omgservers.model.dto.player.ReceiveMessagesPlayerRequest;
import com.omgservers.model.dto.player.ReceiveMessagesPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface ReceiveMessagesMethod {
    Uni<ReceiveMessagesPlayerResponse> receiveMessages(ReceiveMessagesPlayerRequest request);
}

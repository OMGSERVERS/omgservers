package com.omgservers.service.module.client.impl.service.clientService.impl.method.receiveMessages;

import com.omgservers.model.dto.client.ReceiveMessagesRequest;
import com.omgservers.model.dto.client.ReceiveMessagesResponse;
import com.omgservers.model.dto.player.ReceiveMessagesPlayerResponse;
import io.smallrye.mutiny.Uni;

public interface ReceiveMessagesMethod {
    Uni<ReceiveMessagesResponse> receiveMessages(ReceiveMessagesRequest request);
}

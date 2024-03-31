package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMessage.syncClientMessage;

import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientMessageMethod {
    Uni<SyncClientMessageResponse> syncClientMessage(SyncClientMessageRequest request);
}

package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMessage.syncClientMessage;

import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientMessageMethod {
    Uni<SyncClientMessageResponse> syncClientMessage(SyncClientMessageRequest request);
}

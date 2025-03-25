package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage;

import com.omgservers.schema.module.client.clientMessage.SyncClientMessageRequest;
import com.omgservers.schema.module.client.clientMessage.SyncClientMessageResponse;
import io.smallrye.mutiny.Uni;

public interface SyncClientMessageMethod {
    Uni<SyncClientMessageResponse> execute(SyncClientMessageRequest request);
}

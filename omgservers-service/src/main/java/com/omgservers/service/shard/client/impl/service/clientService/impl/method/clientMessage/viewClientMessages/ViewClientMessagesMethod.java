package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage.viewClientMessages;

import com.omgservers.schema.module.client.ViewClientMessagesRequest;
import com.omgservers.schema.module.client.ViewClientMessagesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewClientMessagesMethod {
    Uni<ViewClientMessagesResponse> viewClientMessages(ViewClientMessagesRequest request);
}

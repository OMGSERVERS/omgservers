package com.omgservers.service.module.client.impl.service.clientService.impl.method.viewClientMessages;

import com.omgservers.model.dto.client.ViewClientMessagesRequest;
import com.omgservers.model.dto.client.ViewClientMessagesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewClientMessagesMethod {
    Uni<ViewClientMessagesResponse> viewClientMessages(ViewClientMessagesRequest request);
}

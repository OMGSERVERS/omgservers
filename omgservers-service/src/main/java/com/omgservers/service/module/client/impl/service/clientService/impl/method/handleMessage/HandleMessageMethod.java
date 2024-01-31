package com.omgservers.service.module.client.impl.service.clientService.impl.method.handleMessage;

import com.omgservers.model.dto.client.HandleMessageRequest;
import com.omgservers.model.dto.client.HandleMessageResponse;
import io.smallrye.mutiny.Uni;

public interface HandleMessageMethod {
    Uni<HandleMessageResponse> handleMessage(HandleMessageRequest request);
}

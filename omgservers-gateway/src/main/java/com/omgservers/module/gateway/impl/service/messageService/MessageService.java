package com.omgservers.module.gateway.impl.service.messageService;

import com.omgservers.module.gateway.impl.service.messageService.request.HandleMessageRequest;
import io.smallrye.mutiny.Uni;

public interface MessageService {

    Uni<Void> handleMessage(HandleMessageRequest request);
}

package com.omgservers.service.module.gateway.impl.service.messageService;

import com.omgservers.service.module.gateway.impl.service.messageService.request.HandleMessageRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface MessageService {

    Uni<Void> handleMessage(@Valid HandleMessageRequest request);
}

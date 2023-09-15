package com.omgservers.module.gateway.impl.service.websocketService;

import com.omgservers.module.gateway.impl.service.websocketService.request.CleanUpRequest;
import com.omgservers.module.gateway.impl.service.websocketService.request.ReceiveTextMessageRequest;
import jakarta.validation.Valid;

public interface WebsocketService {

    void cleanUp(@Valid CleanUpRequest request);

    void receiveTextMessage(@Valid ReceiveTextMessageRequest request);
}

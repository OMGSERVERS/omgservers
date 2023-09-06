package com.omgservers.module.gateway.impl.service.websocketService;

import com.omgservers.module.gateway.impl.service.websocketService.request.CleanUpRequest;
import com.omgservers.module.gateway.impl.service.websocketService.request.ReceiveTextMessageRequest;

public interface WebsocketService {

    void cleanUp(CleanUpRequest request);

    void receiveTextMessage(ReceiveTextMessageRequest request);
}

package com.omgservers.module.gateway.impl.service.websocketService;

import com.omgservers.module.gateway.impl.service.websocketService.request.CleanUpHelpRequest;
import com.omgservers.module.gateway.impl.service.websocketService.request.ReceiveTextMessageHelpRequest;

public interface WebsocketEndpointService {

    void cleanUp(CleanUpHelpRequest request);

    void receiveTextMessage(ReceiveTextMessageHelpRequest request);
}

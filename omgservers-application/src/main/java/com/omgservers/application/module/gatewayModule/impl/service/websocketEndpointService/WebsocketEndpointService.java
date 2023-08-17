package com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService;

import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.CleanUpHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.ReceiveTextMessageHelpRequest;

public interface WebsocketEndpointService {

    void cleanUp(CleanUpHelpRequest request);

    void receiveTextMessage(ReceiveTextMessageHelpRequest request);
}

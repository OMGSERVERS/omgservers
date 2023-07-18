package com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService;

import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.CleanUpHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.HandleSessionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.ReceiveTextMessageHelpRequest;

public interface WebsocketEndpointService {

    void handleSession(HandleSessionHelpRequest request);

    void cleanUp(CleanUpHelpRequest request);

    void receiveTextMessage(ReceiveTextMessageHelpRequest request);
}

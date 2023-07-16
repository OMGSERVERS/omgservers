package com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService;

import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.DeleteConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.CreateConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.websocketEndpointService.request.ReceiveTextMessageHelpRequest;

public interface WebsocketEndpointService {

    void createConnection(CreateConnectionHelpRequest request);

    void deleteConnection(DeleteConnectionHelpRequest request);

    void receiveTextMessage(ReceiveTextMessageHelpRequest request);
}

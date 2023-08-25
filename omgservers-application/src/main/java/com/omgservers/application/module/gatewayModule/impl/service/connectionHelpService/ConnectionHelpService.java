package com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService;

import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.AssignPlayerHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.CreateConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.DeleteConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.GetAssignedPlayerHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.GetConnectionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.GetSessionHelpRequest;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response.DeleteConnectionHelpResponse;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response.GetAssignedPlayerHelpResponse;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response.GetConnectionHelpResponse;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response.GetSessionHelpResponse;

public interface ConnectionHelpService {

    void createConnection(CreateConnectionHelpRequest request);

    DeleteConnectionHelpResponse deleteConnection(DeleteConnectionHelpRequest request);

    void assignPlayer(AssignPlayerHelpRequest request);

    GetConnectionHelpResponse getConnection(GetConnectionHelpRequest request);

    GetSessionHelpResponse getSession(GetSessionHelpRequest request);

    GetAssignedPlayerHelpResponse getAssignedPlayer(GetAssignedPlayerHelpRequest request);
}

package com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService;

import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.*;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response.DeleteConnectionHelpResponse;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response.GetSessionHelpResponse;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response.GetConnectionHelpResponse;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response.GetAssignedPlayerHelpResponse;

public interface ConnectionHelpService {

    void createConnection(CreateConnectionHelpRequest request);

    DeleteConnectionHelpResponse deleteConnection(DeleteConnectionHelpRequest request);

    void assignPlayer(AssignPlayerHelpRequest request);

    GetConnectionHelpResponse getConnection(GetConnectionHelpRequest request);

    GetSessionHelpResponse getSession(GetSessionHelpRequest request);

    GetAssignedPlayerHelpResponse getAssignedPlayer(GetAssignedPlayerHelpRequest request);
}

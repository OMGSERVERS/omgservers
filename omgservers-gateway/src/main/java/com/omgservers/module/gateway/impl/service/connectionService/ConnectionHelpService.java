package com.omgservers.module.gateway.impl.service.connectionService;

import com.omgservers.module.gateway.impl.service.connectionService.request.AssignPlayerHelpRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.CreateConnectionHelpRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.DeleteConnectionHelpRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetAssignedPlayerHelpRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetConnectionHelpRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetSessionHelpRequest;
import com.omgservers.module.gateway.impl.service.connectionService.response.DeleteConnectionHelpResponse;
import com.omgservers.module.gateway.impl.service.connectionService.response.GetAssignedPlayerHelpResponse;
import com.omgservers.module.gateway.impl.service.connectionService.response.GetConnectionHelpResponse;
import com.omgservers.module.gateway.impl.service.connectionService.response.GetSessionHelpResponse;

public interface ConnectionHelpService {

    void createConnection(CreateConnectionHelpRequest request);

    DeleteConnectionHelpResponse deleteConnection(DeleteConnectionHelpRequest request);

    void assignPlayer(AssignPlayerHelpRequest request);

    GetConnectionHelpResponse getConnection(GetConnectionHelpRequest request);

    GetSessionHelpResponse getSession(GetSessionHelpRequest request);

    GetAssignedPlayerHelpResponse getAssignedPlayer(GetAssignedPlayerHelpRequest request);
}

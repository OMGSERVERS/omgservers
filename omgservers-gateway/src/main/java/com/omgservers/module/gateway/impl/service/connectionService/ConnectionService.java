package com.omgservers.module.gateway.impl.service.connectionService;

import com.omgservers.module.gateway.impl.service.connectionService.request.AssignPlayerRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.AssignRuntimeRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.CreateConnectionRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.DeleteConnectionRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetAssignedPlayerRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetAssignedRuntimeRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetConnectionRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetSessionRequest;
import com.omgservers.module.gateway.impl.service.connectionService.response.DeleteConnectionResponse;
import com.omgservers.module.gateway.impl.service.connectionService.response.GetAssignedPlayerResponse;
import com.omgservers.module.gateway.impl.service.connectionService.response.GetAssignedRuntimeResponse;
import com.omgservers.module.gateway.impl.service.connectionService.response.GetConnectionResponse;
import com.omgservers.module.gateway.impl.service.connectionService.response.GetSessionResponse;

public interface ConnectionService {

    void createConnection(CreateConnectionRequest request);

    DeleteConnectionResponse deleteConnection(DeleteConnectionRequest request);

    void assignPlayer(AssignPlayerRequest request);

    void assignRuntime(AssignRuntimeRequest request);

    GetConnectionResponse getConnection(GetConnectionRequest request);

    GetSessionResponse getSession(GetSessionRequest request);

    GetAssignedPlayerResponse getAssignedPlayer(GetAssignedPlayerRequest request);

    GetAssignedRuntimeResponse getAssignedRuntime(GetAssignedRuntimeRequest request);
}

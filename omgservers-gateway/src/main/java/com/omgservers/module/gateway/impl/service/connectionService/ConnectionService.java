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
import jakarta.validation.Valid;

public interface ConnectionService {

    void createConnection(@Valid CreateConnectionRequest request);

    DeleteConnectionResponse deleteConnection(@Valid DeleteConnectionRequest request);

    void assignPlayer(@Valid AssignPlayerRequest request);

    void assignRuntime(@Valid AssignRuntimeRequest request);

    GetConnectionResponse getConnection(@Valid GetConnectionRequest request);

    GetSessionResponse getSession(@Valid GetSessionRequest request);

    GetAssignedPlayerResponse getAssignedPlayer(@Valid GetAssignedPlayerRequest request);

    GetAssignedRuntimeResponse getAssignedRuntime(@Valid GetAssignedRuntimeRequest request);
}

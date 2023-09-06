package com.omgservers.module.gateway.impl.service.connectionService.impl;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import com.omgservers.module.gateway.impl.service.connectionService.ConnectionService;
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
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
class ConnectionServiceImpl implements ConnectionService {

    final GenerateIdOperation generateIdOperation;

    final Map<Long, AssignedPlayerModel> assignedPlayerByConnection;
    final Map<Long, AssignedRuntimeModel> assignedRuntimeByConnection;
    final Map<Long, Session> sessionByConnection;
    final Map<String, Long> connectionBySession;

    ConnectionServiceImpl(GenerateIdOperation generateIdOperation) {
        this.generateIdOperation = generateIdOperation;

        connectionBySession = new HashMap<>();
        sessionByConnection = new HashMap<>();
        assignedPlayerByConnection = new HashMap<>();
        assignedRuntimeByConnection = new HashMap<>();
    }

    @Override
    public synchronized void createConnection(CreateConnectionRequest request) {
        CreateConnectionRequest.validate(request);

        final var session = request.getSession();
        final var sessionId = session.getId();

        if (!connectionBySession.containsKey(sessionId)) {
            final var connectionId = generateIdOperation.generateId();
            connectionBySession.put(sessionId, connectionId);
            sessionByConnection.put(connectionId, session);
            log.info("Session was associated with connectionId, sessionId={}, connectionId={}", sessionId, connectionId);
        }
    }

    @Override
    public synchronized DeleteConnectionResponse deleteConnection(DeleteConnectionRequest request) {
        DeleteConnectionRequest.validate(request);

        final var session = request.getSession();
        final var sessionId = session.getId();
        final var connection = connectionBySession.remove(sessionId);

        if (connection != null) {
            sessionByConnection.remove(connection);
            final var assignedPlayer = assignedPlayerByConnection.remove(connection);
            final var assignedRuntime = assignedRuntimeByConnection.remove(connection);
            return new DeleteConnectionResponse(connection, assignedPlayer, assignedRuntime);
        } else {
            log.warn("Connection was not found, sessionId={}", sessionId);
            return new DeleteConnectionResponse();
        }
    }

    @Override
    public synchronized void assignPlayer(AssignPlayerRequest request) {
        AssignPlayerRequest.validate(request);

        final var connectionId = request.getConnectionId();
        if (sessionByConnection.containsKey(connectionId)) {
            final var assignedPlayer = request.getAssignedPlayer();
            assignedPlayerByConnection.put(connectionId, assignedPlayer);
        } else {
            log.warn("Connection was not found, connectionId={}", connectionId);
        }
    }

    @Override
    public synchronized void assignRuntime(AssignRuntimeRequest request) {
        AssignRuntimeRequest.validate(request);

        final var connectionId = request.getConnectionId();
        if (sessionByConnection.containsKey(connectionId)) {
            final var assignedRuntime = request.getAssignedRuntime();
            assignedRuntimeByConnection.put(connectionId, assignedRuntime);
        } else {
            log.warn("Connection was not found, connectionId={}", connectionId);
        }
    }

    @Override
    public synchronized GetConnectionResponse getConnection(GetConnectionRequest request) {
        GetConnectionRequest.validate(request);

        final var session = request.getSession();
        final var sessionId = session.getId();
        final var connection = connectionBySession.get(sessionId);
        if (connection != null) {
            return new GetConnectionResponse(connection);
        } else {
            throw new ServerSideNotFoundException("connection was not found, sessionId=" + sessionId);
        }
    }

    @Override
    public synchronized GetSessionResponse getSession(GetSessionRequest request) {
        GetSessionRequest.validate(request);

        final var connectionId = request.getConnectionId();
        final var session = sessionByConnection.get(connectionId);
        if (session != null) {
            return new GetSessionResponse(session);
        } else {
            throw new ServerSideNotFoundException("session was not found, connectionId=" + connectionId);
        }
    }

    @Override
    public synchronized GetAssignedPlayerResponse getAssignedPlayer(GetAssignedPlayerRequest request) {
        GetAssignedPlayerRequest.validate(request);

        final var connectionId = request.getConnectionId();
        final var assignedPlayer = assignedPlayerByConnection.get(connectionId);
        if (assignedPlayer != null) {
            return new GetAssignedPlayerResponse(assignedPlayer);
        } else {
            throw new ServerSideNotFoundException("assigned player was not found, connectionId=" + connectionId);
        }
    }

    @Override
    public synchronized GetAssignedRuntimeResponse getAssignedRuntime(final GetAssignedRuntimeRequest request) {
        GetAssignedRuntimeRequest.validate(request);

        final var connectionId = request.getConnectionId();
        final var assignedRuntime = assignedRuntimeByConnection.get(connectionId);
        if (assignedRuntime != null) {
            return new GetAssignedRuntimeResponse(assignedRuntime);
        } else {
            throw new ServerSideNotFoundException("assigned runtime was not found, connectionId=" + connectionId);
        }
    }
}

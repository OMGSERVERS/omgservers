package com.omgservers.module.gateway.impl.service.connectionService.impl;

import com.omgservers.dto.gateway.AssignClientRequest;
import com.omgservers.dto.gateway.AssignClientResponse;
import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.AssignRuntimeResponse;
import com.omgservers.dto.gateway.RevokeRuntimeRequest;
import com.omgservers.dto.gateway.RevokeRuntimeResponse;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.assignedClient.AssignedClientModel;
import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import com.omgservers.module.gateway.impl.service.connectionService.ConnectionService;
import com.omgservers.module.gateway.impl.service.connectionService.request.CreateConnectionRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.DeleteConnectionRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetAssignedClientRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetAssignedRuntimeRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetConnectionRequest;
import com.omgservers.module.gateway.impl.service.connectionService.request.GetSessionRequest;
import com.omgservers.module.gateway.impl.service.connectionService.response.DeleteConnectionResponse;
import com.omgservers.module.gateway.impl.service.connectionService.response.GetAssignedClientResponse;
import com.omgservers.module.gateway.impl.service.connectionService.response.GetAssignedRuntimeResponse;
import com.omgservers.module.gateway.impl.service.connectionService.response.GetConnectionResponse;
import com.omgservers.module.gateway.impl.service.connectionService.response.GetSessionResponse;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
class ConnectionServiceImpl implements ConnectionService {

    final GenerateIdOperation generateIdOperation;

    final Map<Long, AssignedClientModel> assignedClientByConnection;
    final Map<Long, AssignedRuntimeModel> assignedRuntimeByConnection;
    final Map<Long, Session> sessionByConnection;
    final Map<String, Long> connectionBySession;

    ConnectionServiceImpl(GenerateIdOperation generateIdOperation) {
        this.generateIdOperation = generateIdOperation;

        connectionBySession = new HashMap<>();
        sessionByConnection = new HashMap<>();
        assignedClientByConnection = new HashMap<>();
        assignedRuntimeByConnection = new HashMap<>();
    }

    @Override
    public synchronized void createConnection(@Valid final CreateConnectionRequest request) {
        final var session = request.getSession();
        final var sessionId = session.getId();

        if (!connectionBySession.containsKey(sessionId)) {
            final var connectionId = generateIdOperation.generateId();
            connectionBySession.put(sessionId, connectionId);
            sessionByConnection.put(connectionId, session);
        }
    }

    @Override
    public synchronized DeleteConnectionResponse deleteConnection(@Valid final DeleteConnectionRequest request) {
        final var session = request.getSession();
        final var sessionId = session.getId();
        final var connection = connectionBySession.remove(sessionId);

        if (connection != null) {
            sessionByConnection.remove(connection);
            final var assignedClient = assignedClientByConnection.remove(connection);
            final var assignedRuntime = assignedRuntimeByConnection.remove(connection);
            return new DeleteConnectionResponse(connection, assignedClient, assignedRuntime);
        } else {
            log.warn("Connection was not found, sessionId={}", sessionId);
            return new DeleteConnectionResponse();
        }
    }

    @Override
    public synchronized AssignClientResponse assignClient(@Valid final AssignClientRequest request) {
        final var connectionId = request.getConnectionId();
        if (sessionByConnection.containsKey(connectionId)) {
            final var assignedClient = request.getAssignedClient();
            final var prevValue = assignedClientByConnection.put(connectionId, assignedClient);
            if (prevValue != null) {
                log.warn("Connection already has assigned client, it was overwritten, " +
                        "connectionId={}, prevValue={}", connectionId, prevValue);
            }
            return new AssignClientResponse(true);
        } else {
            log.warn("Connection was not found, connectionId={}", connectionId);
            return new AssignClientResponse(false);
        }
    }

    @Override
    public synchronized AssignRuntimeResponse assignRuntime(@Valid final AssignRuntimeRequest request) {
        final var connectionId = request.getConnectionId();
        if (sessionByConnection.containsKey(connectionId)) {
            final var assignedRuntime = request.getAssignedRuntime();
            final var prevValue = assignedRuntimeByConnection.put(connectionId, assignedRuntime);
            if (prevValue != null) {
                log.warn("Connection already has assigned runtime, it was overwritten, " +
                        "connectionId={}, prevValue={}", connectionId, prevValue);
            }
            return new AssignRuntimeResponse(true);
        } else {
            log.warn("Connection was not found, connectionId={}", connectionId);
            return new AssignRuntimeResponse(false);
        }
    }

    @Override
    public synchronized RevokeRuntimeResponse revokeRuntime(@Valid final RevokeRuntimeRequest request) {
        final var connectionId = request.getConnectionId();
        if (sessionByConnection.containsKey(connectionId)) {
            if (assignedRuntimeByConnection.remove(connectionId) != null) {
                return new RevokeRuntimeResponse(true);
            } else {
                log.warn("Connection has not assigned runtime, connectionId={}", connectionId);
                return new RevokeRuntimeResponse(false);
            }
        } else {
            log.warn("Connection was not found, connectionId={}", connectionId);
            return new RevokeRuntimeResponse(false);
        }
    }

    @Override
    public synchronized GetConnectionResponse getConnection(@Valid final GetConnectionRequest request) {
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
    public synchronized GetSessionResponse getSession(@Valid final GetSessionRequest request) {
        final var connectionId = request.getConnectionId();
        final var session = sessionByConnection.get(connectionId);
        if (session != null) {
            return new GetSessionResponse(session);
        } else {
            throw new ServerSideNotFoundException("session was not found, connectionId=" + connectionId);
        }
    }

    @Override
    public synchronized GetAssignedClientResponse getAssignedClient(@Valid final GetAssignedClientRequest request) {
        final var connectionId = request.getConnectionId();
        final var assignedClient = assignedClientByConnection.get(connectionId);
        if (assignedClient != null) {
            return new GetAssignedClientResponse(assignedClient);
        } else {
            throw new ServerSideNotFoundException("assigned client was not found, connectionId=" + connectionId);
        }
    }

    @Override
    public synchronized GetAssignedRuntimeResponse getAssignedRuntime(@Valid final GetAssignedRuntimeRequest request) {
        final var connectionId = request.getConnectionId();
        final var assignedRuntime = assignedRuntimeByConnection.get(connectionId);
        if (assignedRuntime != null) {
            return new GetAssignedRuntimeResponse(assignedRuntime);
        } else {
            throw new ServerSideNotFoundException("assigned runtime was not found, connectionId=" + connectionId);
        }
    }
}

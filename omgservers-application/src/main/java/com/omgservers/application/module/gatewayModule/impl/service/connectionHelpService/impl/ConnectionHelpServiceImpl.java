package com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.impl;

import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.ConnectionHelpService;
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
import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.assignedPlayer.AssignedPlayerModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class ConnectionHelpServiceImpl implements ConnectionHelpService {

    final GenerateIdOperation generateIdOperation;

    final Map<Long, AssignedPlayerModel> assignedPlayerByConnection;
    final Map<Long, Session> sessionByConnection;
    final Map<String, Long> connectionBySession;

    ConnectionHelpServiceImpl(GenerateIdOperation generateIdOperation) {
        this.generateIdOperation = generateIdOperation;

        connectionBySession = new ConcurrentHashMap<>();
        sessionByConnection = new ConcurrentHashMap<>();
        assignedPlayerByConnection = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void createConnection(CreateConnectionHelpRequest request) {
        CreateConnectionHelpRequest.validate(request);

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
    public synchronized DeleteConnectionHelpResponse deleteConnection(DeleteConnectionHelpRequest request) {
        DeleteConnectionHelpRequest.validate(request);

        final var session = request.getSession();
        final var sessionId = session.getId();
        final var connection = connectionBySession.remove(sessionId);

        if (connection != null) {
            sessionByConnection.remove(connection);
            final var assignedPlayer = assignedPlayerByConnection.remove(connection);
            return new DeleteConnectionHelpResponse(connection, assignedPlayer);
        } else {
            log.warn("Connection was not found, sessionId={}", sessionId);
            return new DeleteConnectionHelpResponse();
        }
    }

    @Override
    public synchronized void assignPlayer(AssignPlayerHelpRequest request) {
        AssignPlayerHelpRequest.validate(request);

        final var connectionId = request.getConnectionId();
        if (sessionByConnection.containsKey(connectionId)) {
            final var assignedPlayer = request.getAssignedPlayer();
            assignedPlayerByConnection.put(connectionId, assignedPlayer);
        } else {
            log.warn("Connection was not found, connectionId={}", connectionId);
        }
    }

    @Override
    public synchronized GetConnectionHelpResponse getConnection(GetConnectionHelpRequest request) {
        GetConnectionHelpRequest.validate(request);

        final var session = request.getSession();
        final var sessionId = session.getId();
        final var connection = connectionBySession.get(sessionId);
        if (connection != null) {
            return new GetConnectionHelpResponse(connection);
        } else {
            throw new ServerSideNotFoundException("connection was not found, sessionId=" + sessionId);
        }
    }

    @Override
    public synchronized GetSessionHelpResponse getSession(GetSessionHelpRequest request) {
        GetSessionHelpRequest.validate(request);

        final var connectionId = request.getConnectionId();
        final var session = sessionByConnection.get(connectionId);
        if (session != null) {
            return new GetSessionHelpResponse(session);
        } else {
            throw new ServerSideNotFoundException("session was not found, connectionId=" + connectionId);
        }
    }

    @Override
    public synchronized GetAssignedPlayerHelpResponse getAssignedPlayer(GetAssignedPlayerHelpRequest request) {
        GetAssignedPlayerHelpRequest.validate(request);

        final var connectionId = request.getConnectionId();
        final var assignedPlayer = assignedPlayerByConnection.get(connectionId);
        if (assignedPlayer != null) {
            return new GetAssignedPlayerHelpResponse(assignedPlayer);
        } else {
            throw new ServerSideNotFoundException("assigned player was not found, connectionId=" + connectionId);
        }
    }
}

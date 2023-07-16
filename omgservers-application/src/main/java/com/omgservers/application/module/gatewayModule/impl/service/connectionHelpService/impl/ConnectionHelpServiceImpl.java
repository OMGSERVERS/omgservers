package com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.impl;

import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request.*;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response.GetSessionHelpResponse;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.ConnectionHelpService;
import com.omgservers.application.module.gatewayModule.model.assignedPlayer.AssignedPlayerModel;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response.GetConnectionHelpResponse;
import com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.response.GetAssignedPlayerHelpResponse;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class ConnectionHelpServiceImpl implements ConnectionHelpService {

    final Map<UUID, AssignedPlayerModel> assignedPlayerByConnection;
    final Map<UUID, Session> sessionByConnection;
    final Map<String, UUID> connectionBySession;

    ConnectionHelpServiceImpl() {
        connectionBySession = new ConcurrentHashMap<>();
        sessionByConnection = new ConcurrentHashMap<>();
        assignedPlayerByConnection = new ConcurrentHashMap<>();
    }

    @Override
    public void createConnection(CreateConnectionHelpRequest request) {
        CreateConnectionHelpRequest.validate(request);

        final var session = request.getSession();
        final var sessionId = session.getId();
        final var connection = UUID.randomUUID();
        if (sessionByConnection.containsKey(connection)) {
            throw new ServerSideConflictException("Generated connection's UUID is already in use");
        } else {
            connectionBySession.put(sessionId, connection);
            sessionByConnection.put(connection, session);
        }
    }

    @Override
    public void deleteConnection(DeleteConnectionHelpRequest request) {
        DeleteConnectionHelpRequest.validate(request);

        final var session = request.getSession();
        final var sessionId = session.getId();
        final var connection = connectionBySession.remove(sessionId);

        if (connection != null) {
            sessionByConnection.remove(connection);
            assignedPlayerByConnection.remove(connection);
        } else {
            log.warn("Connection was not found, sessionId={}", sessionId);
        }
    }

    @Override
    public void assignPlayer(AssignPlayerHelpRequest request) {
        AssignPlayerHelpRequest.validate(request);

        final var connection = request.getConnection();
        if (sessionByConnection.containsKey(connection)) {
            final var assignedPlayer = request.getAssignedPlayer();
            assignedPlayerByConnection.put(connection, assignedPlayer);
        } else {
            log.warn("Connection was not found, connection={}", connection);
        }
    }

    @Override
    public GetConnectionHelpResponse getConnection(GetConnectionHelpRequest request) {
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
    public GetSessionHelpResponse getSession(GetSessionHelpRequest request) {
        GetSessionHelpRequest.validate(request);

        final var connection = request.getConnection();
        final var session = sessionByConnection.get(connection);
        if (session != null) {
            return new GetSessionHelpResponse(session);
        } else {
            throw new ServerSideNotFoundException("session was not found, connection=" + connection);
        }
    }

    @Override
    public GetAssignedPlayerHelpResponse getAssignedPlayer(GetAssignedPlayerHelpRequest request) {
        GetAssignedPlayerHelpRequest.validate(request);

        final var connection = request.getConnection();
        final var assignedPlayer = assignedPlayerByConnection.get(connection);
        if (assignedPlayer != null) {
            return new GetAssignedPlayerHelpResponse(assignedPlayer);
        } else {
            throw new ServerSideNotFoundException("assigned player was not found, connection=" + connection);
        }
    }
}

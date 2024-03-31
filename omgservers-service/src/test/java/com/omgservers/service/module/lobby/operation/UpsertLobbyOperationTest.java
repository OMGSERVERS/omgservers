package com.omgservers.service.module.lobby.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.lobby.LobbyModelFactory;
import com.omgservers.service.module.lobby.operation.testInterface.UpsertLobbyOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertLobbyOperationTest extends Assertions {

    @Inject
    UpsertLobbyOperationTestInterface upsertLobbyOperation;

    @Inject
    LobbyModelFactory lobbyModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void whenUpsertLobby_thenInserted() {
        final var shard = 0;
        final var lobby = lobbyModelFactory.create(tenantId(), versionId());
        final var changeContext = upsertLobbyOperation.upsertLobby(shard, lobby);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.LOBBY_CREATED));
    }

    @Test
    void givenLobby_whenUpsertLobby_thenUpdated() {
        final var shard = 0;
        final var lobby = lobbyModelFactory.create(tenantId(), versionId());
        upsertLobbyOperation.upsertLobby(shard, lobby);

        final var changeContext = upsertLobbyOperation.upsertLobby(shard, lobby);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.LOBBY_CREATED));
    }

    @Test
    void givenLobby_whenUpsertLobby_thenIdempotencyViolation() {
        final var shard = 0;
        final var lobby1 = lobbyModelFactory.create(tenantId(), versionId());
        upsertLobbyOperation.upsertLobby(shard, lobby1);

        final var lobby2 = lobbyModelFactory.create(tenantId(), versionId(), lobby1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertLobbyOperation.upsertLobby(shard, lobby2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }
}
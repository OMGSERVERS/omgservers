package com.omgservers.service.module.lobby.operation;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.lobby.LobbyModelFactory;
import com.omgservers.service.factory.lobby.LobbyRuntimeRefModelFactory;
import com.omgservers.service.module.lobby.operation.testInterface.UpsertLobbyOperationTestInterface;
import com.omgservers.service.module.lobby.operation.testInterface.UpsertLobbyRuntimeRefOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertLobbyRuntimeRefOperationTest extends Assertions {

    @Inject
    UpsertLobbyOperationTestInterface upsertLobbyOperation;

    @Inject
    UpsertLobbyRuntimeRefOperationTestInterface upsertLobbyRuntimeRefOperation;

    @Inject
    LobbyModelFactory lobbyModelFactory;

    @Inject
    LobbyRuntimeRefModelFactory lobbyRuntimeRefModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenLobbyRuntimeRef_whenUpsertLobbyRuntimeRef_thenInserted() {
        final var shard = 0;
        final var lobby = lobbyModelFactory.create(tenantId(), versionId());
        upsertLobbyOperation.upsertLobby(shard, lobby);

        final var lobbyRuntimeRef = lobbyRuntimeRefModelFactory.create(lobby.getId(), runtimeId());
        final var changeContext = upsertLobbyRuntimeRefOperation.upsertLobbyRuntimeRef(shard, lobbyRuntimeRef);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.LOBBY_RUNTIME_REF_CREATED));
    }

    @Test
    void givenLobbyRuntimeRef_whenUpsertLobbyRuntimeRef_thenUpdated() {
        final var shard = 0;
        final var lobby = lobbyModelFactory.create(tenantId(), versionId());
        upsertLobbyOperation.upsertLobby(shard, lobby);

        final var lobbyRuntimeRef = lobbyRuntimeRefModelFactory.create(lobby.getId(), runtimeId());
        upsertLobbyRuntimeRefOperation.upsertLobbyRuntimeRef(shard, lobbyRuntimeRef);
        final var changeContext = upsertLobbyRuntimeRefOperation.upsertLobbyRuntimeRef(shard, lobbyRuntimeRef);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.LOBBY_RUNTIME_REF_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertLobbyRuntimeRef_thenException() {
        final var shard = 0;
        final var lobbyRuntimeRef = lobbyRuntimeRefModelFactory.create(lobbyId(), runtimeId());
        assertThrows(ServerSideBadRequestException.class,
                () -> upsertLobbyRuntimeRefOperation.upsertLobbyRuntimeRef(shard, lobbyRuntimeRef));
    }

    @Test
    void givenLobbyRuntimeRef_whenUpsertLobbyRuntimeRef_thenIdempotencyViolation() {
        final var shard = 0;
        final var lobby = lobbyModelFactory.create(tenantId(), versionId());
        upsertLobbyOperation.upsertLobby(shard, lobby);

        final var lobbyRuntimeRef1 = lobbyRuntimeRefModelFactory.create(lobby.getId(), runtimeId());
        upsertLobbyRuntimeRefOperation.upsertLobbyRuntimeRef(shard, lobbyRuntimeRef1);

        final var lobbyRuntimeRef2 = lobbyRuntimeRefModelFactory.create(lobby.getId(),
                runtimeId(),
                lobbyRuntimeRef1.getIdempotencyKey());


        final var changeContext = upsertLobbyRuntimeRefOperation.upsertLobbyRuntimeRef(shard, lobbyRuntimeRef1);
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertLobbyRuntimeRefOperation.upsertLobbyRuntimeRef(shard, lobbyRuntimeRef2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long lobbyId() {
        return generateIdOperation.generateId();
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }
}
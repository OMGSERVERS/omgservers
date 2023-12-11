package com.omgservers.service.module.admin.impl.service.adminService;

import com.omgservers.model.dto.admin.BcryptHashAdminRequest;
import com.omgservers.model.dto.admin.CreateIndexAdminRequest;
import com.omgservers.model.dto.admin.FindIndexAdminRequest;
import com.omgservers.model.dto.admin.SyncIndexAdminRequest;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.admin.impl.service.adminService.testInterface.AdminServiceTestInterface;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@QuarkusTest
class AdminServiceTest extends Assertions {

    @Inject
    AdminServiceTestInterface adminService;

    @Test
    void givenService_whenPingServer_thenPong() {
        final var response = adminService.pingServer();
        assertEquals("PONG", response.getMessage());
    }

    @Test
    void givenService_whenGenerateId_thenId() {
        final var response = adminService.generateId();
        assertNotNull(response.getId());
    }

    @Test
    void givenService_whenBcryptHash_thenHash() {
        final var request = new BcryptHashAdminRequest("secret");
        final var response = adminService.bcryptHash(request);
        assertNotNull(response.getHash());
        assertTrue(BcryptUtil.matches("secret", response.getHash()));
    }

    @Test
    void givenUnknownIndexName_whenFindIndex_thenException() {
        final var name = UUID.randomUUID().toString();
        final var request = new FindIndexAdminRequest(name);
        assertThrows(ServerSideNotFoundException.class, () -> {
            adminService.findIndex(request);
        });
    }

    @Test
    void givenIndex_whenFindIndex_thenFound() {
        final var name = UUID.randomUUID().toString();
        final var shards = 32;
        final var addresses = Arrays.asList(URI.create("http://localhost:10001"), URI.create("http://localhost:1000"));
        final var createIndexAdminRequest = new CreateIndexAdminRequest(name, shards, addresses);
        final var createIndexAdminResponse = adminService.createIndex(createIndexAdminRequest);
        final var findIndexAdminRequest = new FindIndexAdminRequest(name);
        final var findIndexAdminResponse = adminService.findIndex(findIndexAdminRequest);
        assertEquals(createIndexAdminResponse.getIndex(), findIndexAdminResponse.getIndex());
    }

    @Test
    void givenIndex_whenSyncIndex_thenUpdated() {
        final var name = UUID.randomUUID().toString();
        final var shards = 32;
        final var addresses = Arrays.asList(URI.create("http://localhost:10001"), URI.create("http://localhost:1000"));
        final var createIndexAdminRequest = new CreateIndexAdminRequest(name, shards, addresses);
        final var createIndexAdminResponse = adminService.createIndex(createIndexAdminRequest);
        final var index = createIndexAdminResponse.getIndex();
        final var syncIndexAdminRequest = new SyncIndexAdminRequest(index);
        final var syncIndexAdminResponse = adminService.syncIndex(syncIndexAdminRequest);
        assertFalse(syncIndexAdminResponse.getCreated());
    }
}
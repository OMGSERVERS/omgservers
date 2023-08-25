package com.omgservers.platform.integrationtest;

import com.omgservers.exception.ClientSideNotFoundException;
import com.omgservers.exception.ClientSideUnauthorizedException;
import com.omgservers.platforms.integrationtest.cli.AdminCli;
import com.omgservers.platforms.integrationtest.cli.DeveloperCli;
import com.omgservers.platforms.integrationtest.operations.bootstrapEnvironmentOperation.BootstrapEnvironmentOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Slf4j
@QuarkusTest
public class DeveloperApiCreateTokenTest extends Assertions {

    @Inject
    BootstrapEnvironmentOperation bootstrapEnvironmentOperation;

    @Inject
    DeveloperCli developerCli;

    @Inject
    AdminCli adminCli;

    @Test
    void givenDeveloper_whenCreateToken_thenCreated() {
        bootstrapEnvironmentOperation.bootstrap();
        adminCli.createClient();
        developerCli.createClient();

        final var tenantUuid = adminCli.createTenant(tenantTitle());
        final var createNewDeveloperAdminResponse = adminCli.createDeveloper(tenantUuid);
        final var userId = createNewDeveloperAdminResponse.getUserId();
        final var password = createNewDeveloperAdminResponse.getPassword();

        final var token = developerCli.createToken(userId, password);
        assertNotNull(token);
    }

    @Test
    void givenDeveloperWrongCredentials_whenCreateToken_thenUnauthorizedException() {
        bootstrapEnvironmentOperation.bootstrap();

        adminCli.createClient();
        final var tenantUuid = adminCli.createTenant(tenantTitle());

        final var createNewDeveloperAdminResponse = adminCli.createDeveloper(tenantUuid);
        final var userId = createNewDeveloperAdminResponse.getUserId();

        developerCli.createClient();
        final var exception = assertThrows(ClientSideUnauthorizedException.class, () -> developerCli
                .createToken(userId, randomString()));
        log.info("Exception: {}", exception.getMessage());
    }

    @Test
    void givenUnknownUser_whenCreateToken_thenNotFoundException() {
        bootstrapEnvironmentOperation.bootstrap();

        final var unknownUser = adminCli.generateId().getId();
        developerCli.createClient();
        final var exception = assertThrows(ClientSideNotFoundException.class, () -> developerCli
                .createToken(unknownUser, randomString()));
        log.info("Exception: {}", exception.getMessage());
    }

    String tenantTitle() {
        return "tenant-" + UUID.randomUUID();
    }

    String randomString() {
        return UUID.randomUUID().toString();
    }
}

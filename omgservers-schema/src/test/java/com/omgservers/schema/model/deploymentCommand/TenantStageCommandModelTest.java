package com.omgservers.schema.model.deploymentCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.deploymentCommand.body.OpenLobbyDeploymentCommandBodyDto;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@QuarkusTest
class TenantStageCommandModelTest extends Assertions {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenTenantDeploymentCommandModel_whenDeserialize_thenEqual() throws IOException {
        final var id = 1000L;
        final var idempotencyKey = "idempotency_value";
        final var deploymentId = 2000L;
        final var created = Instant.now();
        final var modified = Instant.now();
        final var qualifier = DeploymentCommandQualifierEnum.OPEN_LOBBY;
        final var lobbyId = 3000L;
        final var body = new OpenLobbyDeploymentCommandBodyDto(lobbyId);
        final var deleted = false;

        final var tenantDeploymentCommandModel = new DeploymentCommandModel(id,
                idempotencyKey,
                deploymentId,
                created,
                modified,
                qualifier,
                body,
                deleted);
        log.info("Tenant deployment command model, {}", tenantDeploymentCommandModel);

        final var messageString = objectMapper.writeValueAsString(tenantDeploymentCommandModel);
        log.info("Deserialized value, {}", messageString);

        final var tenantDeploymentCommandObject = objectMapper
                .readValue(messageString, DeploymentCommandModel.class);

        assertEquals(tenantDeploymentCommandModel, tenantDeploymentCommandObject);
    }
}
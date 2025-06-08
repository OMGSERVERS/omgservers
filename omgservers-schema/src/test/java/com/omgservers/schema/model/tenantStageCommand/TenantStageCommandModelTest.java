package com.omgservers.schema.model.tenantStageCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.tenantStageCommand.body.OpenDeploymentTenantStageCommandBodyDto;
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
    void givenTenantStageCommandModel_whenDeserialize_thenEqual() throws IOException {
        final var id = 1000L;
        final var tenantId = 2000L;
        final var stageId = 3000L;
        final var idempotencyKey = "idempotency_value";
        final var created = Instant.now();
        final var modified = Instant.now();
        final var qualifier = TenantStageCommandQualifierEnum.OPEN_DEPLOYMENT;
        final var deploymentId = 4000L;
        final var body = new OpenDeploymentTenantStageCommandBodyDto(deploymentId);
        final var deleted = false;

        final var tenantStageCommand = new TenantStageCommandModel(id,
                idempotencyKey,
                tenantId,
                stageId,
                created,
                modified,
                qualifier,
                body,
                deleted);
        log.info("Tenant stage command model, {}", tenantStageCommand);

        final var messageString = objectMapper.writeValueAsString(tenantStageCommand);
        log.info("Deserialized value, {}", messageString);

        final var tenantStageCommandObject = objectMapper.readValue(messageString, TenantStageCommandModel.class);

        assertEquals(tenantStageCommand, tenantStageCommandObject);
    }
}
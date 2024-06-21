package com.omgservers.model.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.module.tenant.TenantCreatedEventBodyModel;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@QuarkusTest
class EventModelTest extends Assertions {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenEventModel_whenDeserialize_thenEqual() throws IOException {
        final var id = 1000L;
        final var idempotencyKey = "idempotency_value";
        final var created = Instant.now();
        final var modified = Instant.now();
        final var delayed = Instant.now();
        final var qualifier = EventQualifierEnum.TENANT_CREATED;
        final var tenantId = 2000L;
        final var body = new TenantCreatedEventBodyModel(tenantId);
        final var deleted = false;

        final var eventModel = new EventModel(id,
                idempotencyKey,
                created,
                modified,
                delayed,
                qualifier,
                body,
                EventStatusEnum.CREATED,
                deleted);
        log.info("Event model, {}", eventModel);

        final var eventString = objectMapper.writeValueAsString(eventModel);
        log.info("Deserialized value, {}", eventString);

        final var eventObject = objectMapper.readValue(eventString, EventModel.class);

        assertEquals(eventModel, eventObject);
    }
}
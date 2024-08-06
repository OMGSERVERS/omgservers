package com.omgservers.schema.model.matchmakerCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandQualifierEnum;
import com.omgservers.schema.model.matchmakerCommand.body.KickClientMatchmakerCommandBodyModel;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

@Slf4j
@QuarkusTest
class MatchmakerCommandModelTest extends Assertions {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenMatchmakerCommandModel_whenDeserialize_thenEqual() throws IOException {
        final var id = 1000L;
        final var matchmakerId = 2000L;
        final var idempotencyKey = "idempotency_value";
        final var created = Instant.now();
        final var modified = Instant.now();
        final var qualifier = MatchmakerCommandQualifierEnum.KICK_CLIENT;
        final var clientId = 3000L;
        final var matchId = 4000L;
        final var body = new KickClientMatchmakerCommandBodyModel(clientId, matchId);
        final var deleted = false;

        final var matchmakerCommandModel = new MatchmakerCommandModel(id,
                idempotencyKey,
                matchmakerId,
                created,
                modified,
                qualifier,
                body,
                deleted);
        log.info("Matchmaker command model, {}", matchmakerCommandModel);

        final var messageString = objectMapper.writeValueAsString(matchmakerCommandModel);
        log.info("Deserialized value, {}", messageString);

        final var matchmakerCommandObject = objectMapper.readValue(messageString, MatchmakerCommandModel.class);

        assertEquals(matchmakerCommandModel, matchmakerCommandObject);
    }
}
package com.omgservers.model.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
@QuarkusTest
class MessageModelTest extends Assertions {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void test() throws IOException {
        final var message = objectMapper.readValue("""
                {
                    "id": "123456",
                    "qualifier": "MATCHMAKER_MESSAGE",
                    "body": {
                        "mode": "deathmatch"
                    }
                }
                """, MessageModel.class);
        log.info("Message, {}", message);
    }
}
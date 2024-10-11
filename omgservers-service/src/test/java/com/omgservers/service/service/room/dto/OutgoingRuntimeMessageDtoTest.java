package com.omgservers.service.service.room.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

@Slf4j
@QuarkusTest
class OutgoingRuntimeMessageDtoTest extends Assertions {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenOutgoingWebSocketMessageDto_whenReadValue_thenParsed() throws IOException {
        final var testMessageDto = new OutgoingRuntimeMessageDtoTest.TestMessageDto(
                "move_player", 123, 321);
        final var testMessageString = objectMapper.writeValueAsString(testMessageDto);

        final var textMessageDto = new OutgoingRuntimeMessageDto(List.of(123456789L),
                MessageEncodingEnum.TXT,
                testMessageString);
        final var textMessageString = objectMapper.writeValueAsString(textMessageDto);
        final var textMessageObject = objectMapper
                .readValue(textMessageString, OutgoingRuntimeMessageDto.class);

        final var testMessageObject = objectMapper.readValue(textMessageObject.getMessage(),
                OutgoingRuntimeMessageDtoTest.TestMessageDto.class);

        assertEquals(textMessageDto.getClients().getFirst(), textMessageObject.getClients().getFirst());
        assertEquals(testMessageDto.getQualifier(), testMessageObject.getQualifier());
        assertEquals(testMessageDto.getX(), testMessageObject.getX());
        assertEquals(testMessageDto.getY(), testMessageObject.getY());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class TestMessageDto {
        String qualifier;
        Integer x;
        Integer y;
    }
}
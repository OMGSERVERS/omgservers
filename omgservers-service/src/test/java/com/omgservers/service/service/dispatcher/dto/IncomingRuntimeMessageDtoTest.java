package com.omgservers.service.service.dispatcher.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.BaseTestClass;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.IncomingRuntimeMessageDto;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
@QuarkusTest
class IncomingRuntimeMessageDtoTest extends BaseTestClass {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void givenIncomingTextMessageDto_whenReadValue_thenParsed() throws IOException {
        final var testMessageDto = new IncomingRuntimeMessageDtoTest.TestMessageDto(
                "move_player", 123, 321);
        final var testMessageString = objectMapper.writeValueAsString(testMessageDto);

        final var textMessageDto = new IncomingRuntimeMessageDto(123456789L,
                MessageEncodingEnum.TXT,
                testMessageString);
        final var textMessageString = objectMapper.writeValueAsString(textMessageDto);
        final var textMessageObject = objectMapper
                .readValue(textMessageString, IncomingRuntimeMessageDto.class);

        final var testMessageObject = objectMapper.readValue(textMessageObject.getMessage(),
                IncomingRuntimeMessageDtoTest.TestMessageDto.class);

        assertEquals(textMessageDto.getClientId(), textMessageObject.getClientId());
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
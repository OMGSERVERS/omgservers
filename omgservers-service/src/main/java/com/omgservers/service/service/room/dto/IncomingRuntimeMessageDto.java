package com.omgservers.service.service.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomingRuntimeMessageDto {

    Long clientId;
    MessageEncodingEnum encoding;
    String message;
}
package com.omgservers.service.module.dispatcher.impl.service.roomService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingRuntimeMessageDto {

    List<Long> clients;
    MessageEncodingEnum encoding;
    String message;
}

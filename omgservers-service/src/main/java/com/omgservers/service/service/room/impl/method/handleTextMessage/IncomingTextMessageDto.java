package com.omgservers.service.service.room.impl.method.handleTextMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomingTextMessageDto {

    Long clientId;
    String message;
}

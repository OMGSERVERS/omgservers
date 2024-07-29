package com.omgservers.service.server.service.room.impl.method.handleTextMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingWebSocketMessage {

    List<Long> clients;
    String message;
}

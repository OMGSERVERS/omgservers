package com.omgservers.service.service.room.impl.method.handleTextMessage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingTextMessageDto {

    List<Long> clients;
    String message;
}

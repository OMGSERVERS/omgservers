package com.omgservers.schema.entrypoint.player;

import com.omgservers.schema.model.incomingMessage.IncomingMessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeMessagesPlayerResponse {

    List<IncomingMessageModel> incomingMessages;
}

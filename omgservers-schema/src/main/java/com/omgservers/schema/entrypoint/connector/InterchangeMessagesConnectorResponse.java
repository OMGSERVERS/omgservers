package com.omgservers.schema.entrypoint.connector;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeMessagesConnectorResponse {

    List<ClientMessageModel> incomingMessages;
}

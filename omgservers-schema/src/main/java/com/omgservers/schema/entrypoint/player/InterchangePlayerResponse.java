package com.omgservers.schema.entrypoint.player;

import com.omgservers.schema.model.message.MessageModel;
import com.omgservers.schema.model.message.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangePlayerResponse {

    List<MessageModel> incomingMessages;
}

package com.omgservers.schema.module.client;

import com.omgservers.schema.model.message.MessageModel;
import com.omgservers.schema.model.message.MessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeResponse {

    List<MessageModel> incomingMessages;
}

package com.omgservers.schema.shard.runtime.runtimeMessage;

import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeMessagesResponse {

    List<RuntimeMessageModel> incomingMessages;
}

package com.omgservers.schema.entrypoint.runtime;

import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeMessagesRuntimeResponse {

    List<RuntimeMessageModel> incomingMessages;
}

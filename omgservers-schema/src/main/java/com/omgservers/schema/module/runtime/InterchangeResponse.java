package com.omgservers.schema.module.runtime;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeResponse {

    List<RuntimeCommandModel> incomingCommands;
}

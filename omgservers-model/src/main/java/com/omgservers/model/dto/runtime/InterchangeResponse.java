package com.omgservers.model.dto.runtime;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
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

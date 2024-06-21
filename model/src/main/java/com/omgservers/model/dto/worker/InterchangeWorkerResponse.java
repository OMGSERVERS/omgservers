package com.omgservers.model.dto.worker;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeWorkerResponse {

    List<RuntimeCommandModel> incomingCommands;
}

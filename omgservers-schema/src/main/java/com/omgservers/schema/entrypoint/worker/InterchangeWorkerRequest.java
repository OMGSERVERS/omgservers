package com.omgservers.schema.entrypoint.worker;

import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterchangeWorkerRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    List<OutgoingCommandModel> outgoingCommands;

    @NotNull
    List<Long> consumedCommands;
}

package com.omgservers.model.dto.worker;

import com.omgservers.model.doCommand.DoCommandModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoWorkerCommandsWorkerRequest {

    @NotNull
    Long runtimeId;

    @NotNull
    List<DoCommandModel> doCommands;
}

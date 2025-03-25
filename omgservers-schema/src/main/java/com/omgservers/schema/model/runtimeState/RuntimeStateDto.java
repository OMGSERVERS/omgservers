package com.omgservers.schema.model.runtimeState;

import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeStateDto {

    @NotNull
    RuntimeModel runtime;

    @NotNull
    List<RuntimeCommandModel> runtimeCommands;

    @NotNull
    List<RuntimeAssignmentModel> runtimeAssignments;
}

package com.omgservers.model.assignedRuntime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignedRuntimeModel {

    @NotNull
    Long runtimeId;
}

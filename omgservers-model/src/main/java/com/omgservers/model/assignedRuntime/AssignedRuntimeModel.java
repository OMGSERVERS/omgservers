package com.omgservers.model.assignedRuntime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignedRuntimeModel {

    @NotNull
    Long runtimeId;
}

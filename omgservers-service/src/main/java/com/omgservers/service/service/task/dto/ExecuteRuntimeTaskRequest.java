package com.omgservers.service.service.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteRuntimeTaskRequest {

    @NotNull
    Long runtimeId;
}

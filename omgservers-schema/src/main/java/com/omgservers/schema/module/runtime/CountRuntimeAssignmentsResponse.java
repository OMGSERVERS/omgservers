package com.omgservers.schema.module.runtime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountRuntimeAssignmentsResponse {

    @NotNull
    Integer count;
}

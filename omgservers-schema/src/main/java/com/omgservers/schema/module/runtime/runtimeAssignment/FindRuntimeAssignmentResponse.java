package com.omgservers.schema.module.runtime.runtimeAssignment;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindRuntimeAssignmentResponse {

    RuntimeAssignmentModel runtimeAssignment;
}

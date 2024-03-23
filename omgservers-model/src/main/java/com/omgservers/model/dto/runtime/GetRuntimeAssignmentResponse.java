package com.omgservers.model.dto.runtime;

import com.omgservers.model.runtimeAssignment.RuntimeAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRuntimeAssignmentResponse {

    RuntimeAssignmentModel runtimeAssignment;
}

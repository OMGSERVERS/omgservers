package com.omgservers.model.dto.pool.poolRuntimeAssignment;

import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPoolRuntimeAssignmentResponse {

    PoolRuntimeAssignmentModel poolRuntimeAssignment;
}

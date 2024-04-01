package com.omgservers.model.dto.pool.poolRuntimeAssignment;

import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolRuntimeAssignmentsResponse {

    List<PoolRuntimeAssignmentModel> poolRuntimeAssignments;
}

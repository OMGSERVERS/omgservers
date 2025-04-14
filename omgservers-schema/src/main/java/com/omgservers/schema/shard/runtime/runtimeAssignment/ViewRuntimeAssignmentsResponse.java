package com.omgservers.schema.shard.runtime.runtimeAssignment;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewRuntimeAssignmentsResponse {

    List<RuntimeAssignmentModel> runtimeAssignments;
}

package com.omgservers.schema.model.runtimeAssignment;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeAssignmentConfigDto {

    static public RuntimeAssignmentConfigDto create() {
        final var runtimeAssignmentConfig = new RuntimeAssignmentConfigDto();
        return runtimeAssignmentConfig;
    }

    MatchmakerMatchAssignmentModel matchmakerMatchAssignment;
}

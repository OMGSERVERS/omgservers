package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerMatchAssignmentResponse {

    MatchmakerMatchAssignmentModel matchmakerMatchAssignment;
}

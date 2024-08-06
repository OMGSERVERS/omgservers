package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindMatchmakerAssignmentResponse {

    MatchmakerAssignmentModel matchmakerAssignment;
}

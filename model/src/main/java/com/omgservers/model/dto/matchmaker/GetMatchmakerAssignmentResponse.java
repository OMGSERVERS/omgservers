package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.matchmakerAssignment.MatchmakerAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerAssignmentResponse {

    MatchmakerAssignmentModel matchmakerAssignment;
}

package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.matchmakerAssignment.MatchmakerAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchmakerAssignmentsResponse {

    List<MatchmakerAssignmentModel> matchmakerAssignments;
}

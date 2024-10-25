package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchmakerMatchAssignmentsResponse {

    List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments;
}

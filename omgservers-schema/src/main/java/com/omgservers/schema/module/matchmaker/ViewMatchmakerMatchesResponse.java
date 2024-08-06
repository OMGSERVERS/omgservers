package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchmakerMatchesResponse {

    List<MatchmakerMatchModel> matchmakerMatches;
}

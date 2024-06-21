package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
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

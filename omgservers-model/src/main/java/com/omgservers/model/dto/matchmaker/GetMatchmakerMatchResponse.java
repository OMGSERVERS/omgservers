package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerMatchResponse {

    MatchmakerMatchModel matchmakerMatch;
}

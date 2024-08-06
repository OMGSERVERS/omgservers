package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerMatchResponse {

    MatchmakerMatchModel matchmakerMatch;
}

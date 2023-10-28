package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.match.MatchModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchResponse {

    MatchModel match;
}

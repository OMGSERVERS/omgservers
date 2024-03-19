package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.matchmakerState.MatchmakerStateModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerStateResponse {

    MatchmakerStateModel matchmakerStateModel;
}

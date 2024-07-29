package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.model.matchmakerState.MatchmakerStateModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerStateResponse {

    MatchmakerStateModel matchmakerStateModel;
}

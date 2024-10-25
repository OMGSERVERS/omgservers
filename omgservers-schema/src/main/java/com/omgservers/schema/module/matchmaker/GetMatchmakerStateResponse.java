package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerStateResponse {

    MatchmakerStateDto matchmakerState;
}

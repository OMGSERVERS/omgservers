package com.omgservers.dto.matchmaker;

import com.omgservers.model.matchmaker.MatchmakerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerShardedResponse {

    MatchmakerModel matchmaker;
}

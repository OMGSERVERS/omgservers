package com.omgservers.schema.shard.match;

import com.omgservers.schema.model.match.MatchModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchResponse {

    MatchModel match;
}

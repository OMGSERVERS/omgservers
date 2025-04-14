package com.omgservers.schema.shard.matchmaker.matchmaker;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerResponse {

    MatchmakerModel matchmaker;
}

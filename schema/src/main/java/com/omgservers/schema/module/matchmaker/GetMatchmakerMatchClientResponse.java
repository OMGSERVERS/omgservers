package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerMatchClientResponse {

    MatchmakerMatchClientModel matchClient;
}

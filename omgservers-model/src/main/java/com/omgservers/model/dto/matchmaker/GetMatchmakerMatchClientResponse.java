package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerMatchClientResponse {

    MatchmakerMatchClientModel matchClient;
}

package com.omgservers.dto.matchmakerModule;

import com.omgservers.model.match.MatchModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchInternalResponse {

    MatchModel match;
}

package com.omgservers.dto.matchmakerModule;

import com.omgservers.model.matchmaker.MatchmakerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerInternalResponse {

    MatchmakerModel matchmaker;
}

package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response;

import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchInternalResponse {

    MatchModel match;
}

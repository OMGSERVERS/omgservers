package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.matchmaker.MatchmakerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchmakerResponse {

    MatchmakerModel matchmaker;
}

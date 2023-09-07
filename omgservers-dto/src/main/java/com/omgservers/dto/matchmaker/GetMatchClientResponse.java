package com.omgservers.dto.matchmaker;

import com.omgservers.model.matchClient.MatchClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMatchClientResponse {

    MatchClientModel matchClient;
}

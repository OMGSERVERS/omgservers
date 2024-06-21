package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchmakerMatchClientsResponse {

    List<MatchmakerMatchClientModel> matchClients;
}

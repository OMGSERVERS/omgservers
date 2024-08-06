package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
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

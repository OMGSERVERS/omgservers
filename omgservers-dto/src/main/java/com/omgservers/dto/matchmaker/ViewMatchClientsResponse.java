package com.omgservers.dto.matchmaker;

import com.omgservers.model.matchClient.MatchClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchClientsResponse {

    List<MatchClientModel> matchClients;
}

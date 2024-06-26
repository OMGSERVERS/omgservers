package com.omgservers.model.dto.lobby;

import com.omgservers.model.lobby.LobbyModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLobbyResponse {

    LobbyModel lobby;
}

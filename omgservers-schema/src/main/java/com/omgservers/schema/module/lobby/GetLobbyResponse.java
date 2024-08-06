package com.omgservers.schema.module.lobby;

import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.lobby.LobbyModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLobbyResponse {

    LobbyModel lobby;
}

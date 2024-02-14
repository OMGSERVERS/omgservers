package com.omgservers.model.dto.lobby;

import com.omgservers.model.lobbyRuntime.LobbyRuntimeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindLobbyRuntimeResponse {

    LobbyRuntimeModel lobbyRuntime;
}

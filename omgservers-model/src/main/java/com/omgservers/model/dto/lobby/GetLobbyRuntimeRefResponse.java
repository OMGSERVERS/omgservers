package com.omgservers.model.dto.lobby;

import com.omgservers.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLobbyRuntimeRefResponse {

    LobbyRuntimeRefModel lobbyRuntimeRef;
}

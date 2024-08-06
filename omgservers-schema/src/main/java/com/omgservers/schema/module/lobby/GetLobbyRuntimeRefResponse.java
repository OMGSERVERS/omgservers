package com.omgservers.schema.module.lobby;

import com.omgservers.schema.model.lobbyRuntimeRef.LobbyRuntimeRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLobbyRuntimeRefResponse {

    LobbyRuntimeRefModel lobbyRuntimeRef;
}

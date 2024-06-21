package com.omgservers.model.dto.tenant;

import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindVersionLobbyRefResponse {

    VersionLobbyRefModel versionLobbyRef;
}

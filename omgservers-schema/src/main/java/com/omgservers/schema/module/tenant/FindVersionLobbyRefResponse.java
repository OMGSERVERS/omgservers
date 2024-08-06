package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindVersionLobbyRefResponse {

    VersionLobbyRefModel versionLobbyRef;
}

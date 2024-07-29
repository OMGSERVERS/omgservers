package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindVersionLobbyRequestResponse {

    VersionLobbyRequestModel versionLobbyRequest;
}

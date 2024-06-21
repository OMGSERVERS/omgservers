package com.omgservers.model.dto.tenant;

import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewVersionLobbyRefsResponse {

    List<VersionLobbyRefModel> versionLobbyRefs;
}

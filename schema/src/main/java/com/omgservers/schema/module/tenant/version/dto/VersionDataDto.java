package com.omgservers.schema.module.tenant.version.dto;

import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.model.versionImageRef.VersionImageRefModel;
import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionDataDto {

    @NotNull
    VersionModel version;

    @NotNull
    List<VersionImageRefModel> imageRefs;

    @NotNull
    List<VersionLobbyRefModel> lobbyRefs;

    @NotNull
    List<VersionMatchmakerRefModel> matchmakerRefs;
}

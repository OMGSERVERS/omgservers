package com.omgservers.schema.model.tenantDashboard;

import com.omgservers.schema.model.project.ProjectModel;
import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.schema.model.version.VersionProjectionModel;
import com.omgservers.schema.model.project.ProjectModel;
import com.omgservers.schema.model.stage.StageModel;
import com.omgservers.schema.model.version.VersionProjectionModel;
import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDashboardModel {

    @NotNull
    List<ProjectModel> projects;

    @NotNull
    List<StageModel> stages;

    @NotNull
    List<VersionProjectionModel> versions;

    @NotNull
    List<VersionLobbyRefModel> lobbyRefs;

    @NotNull
    List<VersionMatchmakerRefModel> matchmakerRefs;
}

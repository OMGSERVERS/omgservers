package com.omgservers.model.tenantDashboard;

import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.model.versionProjection.VersionProjectionModel;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
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
    List<VersionRuntimeModel> versionRuntimes;

    @NotNull
    List<VersionMatchmakerModel> versionMatchmakers;
}

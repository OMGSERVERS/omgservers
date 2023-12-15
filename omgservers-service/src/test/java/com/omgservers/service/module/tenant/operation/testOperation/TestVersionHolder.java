package com.omgservers.service.module.tenant.operation.testOperation;

import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.version.VersionModel;

public record TestVersionHolder(TenantModel tenant,
                                ProjectModel project,
                                StageModel stage,
                                VersionModel version) {
}

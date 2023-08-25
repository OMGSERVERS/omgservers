package com.omgservers.application.module.versionModule.impl.operation.deployConfigurationOperation;

import com.omgservers.model.version.VersionModel;
import io.smallrye.mutiny.Uni;

public interface DeployConfigurationOperation {
    Uni<VersionModel> deployConfiguration(VersionModel version);
}

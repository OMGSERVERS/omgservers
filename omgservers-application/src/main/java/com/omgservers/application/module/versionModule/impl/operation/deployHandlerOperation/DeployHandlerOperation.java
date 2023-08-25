package com.omgservers.application.module.versionModule.impl.operation.deployHandlerOperation;

import com.omgservers.model.version.VersionModel;
import io.smallrye.mutiny.Uni;

public interface DeployHandlerOperation {
    Uni<Void> deployHandler(VersionModel version);
}

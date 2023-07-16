package com.omgservers.application.module.versionModule.impl.operation.deployHandlerOperation;

import com.omgservers.application.module.versionModule.model.VersionModel;
import io.smallrye.mutiny.Uni;

public interface DeployHandlerOperation {
    Uni<Void> deployHandler(VersionModel version);
}

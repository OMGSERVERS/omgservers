package com.omgservers.application.module.versionModule.impl.operation.getVersionConfigOperation;

import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import com.omgservers.application.module.versionModule.model.VersionModel;

public interface GetVersionConfigOperation {
    VersionStageConfigModel getVersionConfig(VersionModel version);
}

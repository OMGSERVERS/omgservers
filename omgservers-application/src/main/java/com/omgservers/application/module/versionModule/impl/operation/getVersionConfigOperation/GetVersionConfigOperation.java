package com.omgservers.application.module.versionModule.impl.operation.getVersionConfigOperation;

import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionStageConfigModel;

public interface GetVersionConfigOperation {
    VersionStageConfigModel getVersionConfig(VersionModel version);
}

package com.omgservers.module.version.impl.operation.getVersionConfig;

import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionStageConfigModel;

public interface GetVersionConfigOperation {
    VersionStageConfigModel getVersionConfig(VersionModel version);
}

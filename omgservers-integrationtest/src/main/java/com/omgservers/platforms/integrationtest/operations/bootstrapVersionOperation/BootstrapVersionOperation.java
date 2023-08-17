package com.omgservers.platforms.integrationtest.operations.bootstrapVersionOperation;

import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;

public interface BootstrapVersionOperation {
    VersionParameters bootstrap(String script) throws InterruptedException;

    VersionParameters bootstrap(String script, VersionStageConfigModel stageConfig) throws InterruptedException;
}

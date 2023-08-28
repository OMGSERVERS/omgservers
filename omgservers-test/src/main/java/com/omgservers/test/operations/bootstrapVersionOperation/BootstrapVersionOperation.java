package com.omgservers.test.operations.bootstrapVersionOperation;

import com.omgservers.model.version.VersionStageConfigModel;

public interface BootstrapVersionOperation {
    VersionParameters bootstrap(String script) throws InterruptedException;

    VersionParameters bootstrap(String script, VersionStageConfigModel stageConfig) throws InterruptedException;
}

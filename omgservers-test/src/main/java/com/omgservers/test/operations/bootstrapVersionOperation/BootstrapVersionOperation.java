package com.omgservers.test.operations.bootstrapVersionOperation;

import com.omgservers.model.version.VersionConfigModel;

public interface BootstrapVersionOperation {
    VersionParameters bootstrap(String script) throws InterruptedException;

    VersionParameters bootstrap(String script, VersionConfigModel versionConfig) throws InterruptedException;
}

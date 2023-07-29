package com.omgservers.platforms.integrationtest.operations.bootstrapVersionOperation;

public interface BootstrapVersionOperation {
    VersionParameters bootstrap(String script) throws InterruptedException;
}

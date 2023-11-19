package com.omgservers.tester.operation.bootstrapTestVersion;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface BootstrapTestVersionOperation {

    TestVersionModel bootstrapTestVersion(String script) throws IOException;

    TestVersionModel bootstrapTestVersion(String script, VersionConfigModel versionConfig) throws IOException;
}

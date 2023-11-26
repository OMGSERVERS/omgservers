package com.omgservers.tester.operation.bootstrapTestVersion;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface BootstrapTestVersionOperation {

    TestVersionModel bootstrapTestVersion(String lobby, String match) throws IOException;

    TestVersionModel bootstrapTestVersion(String lobby,
                                          String match,
                                          VersionConfigModel versionConfig) throws IOException;
}

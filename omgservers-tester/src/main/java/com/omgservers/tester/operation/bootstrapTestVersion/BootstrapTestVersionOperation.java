package com.omgservers.tester.operation.bootstrapTestVersion;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface BootstrapTestVersionOperation {

    TestVersionModel bootstrapTestVersion(String mainLua) throws IOException;

    TestVersionModel bootstrapTestVersion(String mainLua,
                                          VersionConfigModel versionConfig) throws IOException;
}

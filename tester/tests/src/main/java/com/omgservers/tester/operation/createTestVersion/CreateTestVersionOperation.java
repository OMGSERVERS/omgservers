package com.omgservers.tester.operation.createTestVersion;

import com.omgservers.schema.model.version.VersionConfigModel;
import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface CreateTestVersionOperation {

    TestVersionModel createTestVersion() throws IOException;

    TestVersionModel createTestVersion(VersionConfigModel versionConfig) throws IOException;
}

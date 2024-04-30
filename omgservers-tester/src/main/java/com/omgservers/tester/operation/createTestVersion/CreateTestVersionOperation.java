package com.omgservers.tester.operation.createTestVersion;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface CreateTestVersionOperation {

    Long createTestVersion(TestVersionModel testVersion,
                           String mainLua) throws IOException;

    Long createTestVersion(TestVersionModel testVersion,
                           String mainLua,
                           VersionConfigModel versionConfig) throws IOException;
}

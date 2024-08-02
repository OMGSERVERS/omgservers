package com.omgservers.tester.operation.createTestVersion;

import com.omgservers.schema.model.version.VersionConfigModel;
import com.omgservers.tester.dto.TestVersionDto;

import java.io.IOException;

public interface CreateTestVersionOperation {

    TestVersionDto createTestVersion() throws IOException;

    TestVersionDto createTestVersion(VersionConfigModel versionConfig) throws IOException;
}

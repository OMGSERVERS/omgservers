package com.omgservers.tester.operation.createTestVersion;

import com.omgservers.schema.model.version.VersionConfigDto;
import com.omgservers.tester.dto.TestVersionDto;

import java.io.IOException;

public interface CreateTestVersionOperation {

    TestVersionDto createTestVersion() throws IOException;

    TestVersionDto createTestVersion(VersionConfigDto versionConfig) throws IOException;
}

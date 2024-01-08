package com.omgservers.tester.operation.createTestVersion;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface CreateTestVersionOperation {

    Long createTestVersion(TestVersionModel testVersion,
                           String newLobbyScript,
                           String newMatchScript) throws IOException;

    Long createTestVersion(TestVersionModel testVersion,
                           String newLobbyScript,
                           String newMatchScript,
                           VersionConfigModel newVersionConfig) throws IOException;
}

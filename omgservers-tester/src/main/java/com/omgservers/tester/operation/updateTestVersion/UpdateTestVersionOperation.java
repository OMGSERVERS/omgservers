package com.omgservers.tester.operation.updateTestVersion;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface UpdateTestVersionOperation {

    Long updateTestVersion(TestVersionModel testVersion,
                           String newLobbyScript,
                           String newMatchScript) throws IOException;

    Long updateTestVersion(TestVersionModel testVersion,
                           String newLobbyScript,
                           String newMatchScript,
                           VersionConfigModel newVersionConfig) throws IOException;
}

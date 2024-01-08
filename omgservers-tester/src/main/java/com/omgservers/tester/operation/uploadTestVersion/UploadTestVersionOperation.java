package com.omgservers.tester.operation.uploadTestVersion;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface UploadTestVersionOperation {

    Long uploadTestVersion(TestVersionModel testVersion,
                           String newLobbyScript,
                           String newMatchScript) throws IOException;

    Long uploadTestVersion(TestVersionModel testVersion,
                           String newLobbyScript,
                           String newMatchScript,
                           VersionConfigModel newVersionConfig) throws IOException;
}

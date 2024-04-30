package com.omgservers.tester.operation.uploadTestVersion;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.tester.model.TestVersionModel;

import java.io.IOException;

public interface UploadTestVersionOperation {

    Long uploadTestVersion(TestVersionModel testVersion,
                           String mainLua) throws IOException;

    Long uploadTestVersion(TestVersionModel testVersion,
                           String mainLua,
                           VersionConfigModel newVersionConfig) throws IOException;
}

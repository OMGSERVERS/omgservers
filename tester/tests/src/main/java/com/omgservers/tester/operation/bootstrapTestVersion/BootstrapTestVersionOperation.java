package com.omgservers.tester.operation.bootstrapTestVersion;

import com.omgservers.schema.model.version.VersionConfigModel;
import com.omgservers.tester.dto.TestVersionDto;

import java.io.IOException;

public interface BootstrapTestVersionOperation {

    TestVersionDto bootstrapTestVersion(String mainLua) throws IOException;

    TestVersionDto bootstrapTestVersion(String mainLua,
                                        VersionConfigModel versionConfig) throws IOException;
}

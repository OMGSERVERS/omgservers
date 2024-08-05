package com.omgservers.tester.operation.bootstrapTestVersion;

import com.omgservers.schema.model.version.VersionConfigDto;
import com.omgservers.tester.dto.TestVersionDto;

import java.io.IOException;

public interface BootstrapTestVersionOperation {

    TestVersionDto bootstrapTestVersion(String mainLua) throws IOException;

    TestVersionDto bootstrapTestVersion(String mainLua,
                                        VersionConfigDto versionConfig) throws IOException;
}

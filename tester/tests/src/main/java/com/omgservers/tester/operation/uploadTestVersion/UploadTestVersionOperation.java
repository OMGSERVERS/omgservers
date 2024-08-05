package com.omgservers.tester.operation.uploadTestVersion;

import com.omgservers.schema.model.version.VersionConfigDto;
import com.omgservers.tester.dto.TestVersionDto;

import java.io.IOException;

public interface UploadTestVersionOperation {

    Long uploadTestVersion(TestVersionDto testVersion,
                           String mainLua) throws IOException;

    Long uploadTestVersion(TestVersionDto testVersion,
                           String mainLua,
                           VersionConfigDto newVersionConfig) throws IOException;
}

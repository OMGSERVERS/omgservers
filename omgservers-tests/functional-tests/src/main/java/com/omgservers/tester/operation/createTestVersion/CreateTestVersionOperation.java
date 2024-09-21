package com.omgservers.tester.operation.createTestVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.tester.dto.TestVersionDto;

import java.io.IOException;

public interface CreateTestVersionOperation {

    TestVersionDto createTestVersion() throws IOException;

    TestVersionDto createTestVersion(TenantVersionConfigDto versionConfig) throws IOException;
}

package com.omgservers.tester.operation.buildTestVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.tester.dto.TestVersionDto;

import java.io.IOException;

public interface BuildTestVersionOperation {

    Long buildTestVersion(TestVersionDto testVersion,
                          String mainLua) throws IOException;

    Long buildTestVersion(TestVersionDto testVersion,
                          String mainLua,
                          TenantVersionConfigDto newVersionConfig) throws IOException;
}

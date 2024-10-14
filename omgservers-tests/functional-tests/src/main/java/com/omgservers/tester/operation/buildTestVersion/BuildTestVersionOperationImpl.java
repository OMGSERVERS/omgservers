package com.omgservers.tester.operation.buildTestVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.tester.component.DeveloperApiTester;
import com.omgservers.tester.dto.TestVersionDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BuildTestVersionOperationImpl implements BuildTestVersionOperation {

    DeveloperApiTester developerApiTester;

    @Override
    public Long buildTestVersion(final TestVersionDto testVersion,
                                 final String mainLua) throws IOException {
        return buildTestVersion(testVersion,
                mainLua,
                TenantVersionConfigDto.create());
    }

    @Override
    public Long buildTestVersion(final TestVersionDto testVersion,
                                 final String mainLua,
                                 final TenantVersionConfigDto newVersionConfig) throws IOException {
        final var buildVersionDeveloperResponse = developerApiTester
                .uploadFilesArchive(testVersion.getDeveloperToken(),
                        testVersion.getTenantId(),
                        testVersion.getTenantVersionId(),
                        mainLua);

        return buildVersionDeveloperResponse.getFilesArchiveId();
    }
}

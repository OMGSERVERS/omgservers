package com.omgservers.tester.operation.buildTestVersion;

import com.omgservers.schema.model.version.VersionConfigDto;
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
                VersionConfigDto.create());
    }

    @Override
    public Long buildTestVersion(final TestVersionDto testVersion,
                                 final String mainLua,
                                 final VersionConfigDto newVersionConfig) throws IOException {
        final var buildVersionDeveloperResponse = developerApiTester.buildVersion(testVersion.getDeveloperToken(),
                testVersion.getTenantId(),
                testVersion.getStageId(),
                newVersionConfig,
                mainLua);

        return buildVersionDeveloperResponse.getId();
    }
}
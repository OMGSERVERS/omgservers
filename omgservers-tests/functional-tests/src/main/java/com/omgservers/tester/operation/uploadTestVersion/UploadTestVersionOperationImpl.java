package com.omgservers.tester.operation.uploadTestVersion;

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
class UploadTestVersionOperationImpl implements UploadTestVersionOperation {

    DeveloperApiTester developerApiTester;

    @Override
    public Long uploadTestVersion(final TestVersionDto testVersion,
                                  final String mainLua) throws IOException {
        return uploadTestVersion(testVersion,
                mainLua,
                VersionConfigDto.create());
    }

    @Override
    public Long uploadTestVersion(final TestVersionDto testVersion,
                                  final String mainLua,
                                  final VersionConfigDto newVersionConfig) throws IOException {
        final var uploadVersionDeveloperResponse = developerApiTester.uploadVersion(testVersion.getDeveloperToken(),
                testVersion.getTenantId(),
                testVersion.getStageId(),
                newVersionConfig,
                mainLua);

        return uploadVersionDeveloperResponse.getId();
    }
}

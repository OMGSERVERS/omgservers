package com.omgservers.tester.operation.uploadTestVersion;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.tester.component.DeveloperApiTester;
import com.omgservers.tester.model.TestVersionModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UploadTestVersionOperationImpl implements UploadTestVersionOperation {

    @Inject
    DeveloperApiTester developerApiTester;

    @Override
    public Long uploadTestVersion(final TestVersionModel testVersion,
                                  final String newLobbyScript,
                                  final String newMatchScript) throws IOException {
        return uploadTestVersion(testVersion,
                newLobbyScript,
                newMatchScript,
                VersionConfigModel.create());
    }

    @Override
    public Long uploadTestVersion(final TestVersionModel testVersion,
                                  final String newLobbyScript,
                                  final String newMatchScript,
                                  final VersionConfigModel newVersionConfig) throws IOException {
        final var uploadVersionDeveloperResponse = developerApiTester.uploadVersion(testVersion.getDeveloperToken(),
                testVersion.getTenantId(),
                testVersion.getStageId(),
                newVersionConfig,
                newLobbyScript,
                newMatchScript);

        return uploadVersionDeveloperResponse.getId();
    }
}

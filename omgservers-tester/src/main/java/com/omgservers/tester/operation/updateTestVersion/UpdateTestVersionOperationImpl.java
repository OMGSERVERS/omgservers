package com.omgservers.tester.operation.updateTestVersion;

import com.omgservers.model.file.EncodedFileModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.tester.component.AdminApiTester;
import com.omgservers.tester.component.DeveloperApiTester;
import com.omgservers.tester.model.TestVersionModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpdateTestVersionOperationImpl implements UpdateTestVersionOperation {

    @Inject
    AdminApiTester adminApiTester;

    @Inject
    DeveloperApiTester developerApiTester;

    @Override
    public Long updateTestVersion(final TestVersionModel testVersion,
                                  final String newLobbyScript,
                                  final String newMatchScript) throws IOException {
        return updateTestVersion(testVersion,
                newLobbyScript,
                newMatchScript,
                VersionConfigModel.create());
    }

    @Override
    public Long updateTestVersion(final TestVersionModel testVersion,
                                  final String newLobbyScript,
                                  final String newMatchScript,
                                  final VersionConfigModel newVersionConfig) throws IOException {
        final var newSourceCode = VersionSourceCodeModel.create();
        newSourceCode.getFiles().add(new EncodedFileModel("lobby.lua", Base64.getEncoder()
                .encodeToString(newLobbyScript.getBytes(StandardCharsets.UTF_8))));
        newSourceCode.getFiles().add(new EncodedFileModel("match.lua", Base64.getEncoder()
                .encodeToString(newMatchScript.getBytes(StandardCharsets.UTF_8))));

        final var createVersionResponse = developerApiTester.createVersion(testVersion.getDeveloperToken(),
                testVersion.getTenantId(),
                testVersion.getStageId(),
                newVersionConfig,
                newSourceCode
        );

        return createVersionResponse.getId();
    }
}

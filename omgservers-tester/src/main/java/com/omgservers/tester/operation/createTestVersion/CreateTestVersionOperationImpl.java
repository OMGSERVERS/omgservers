package com.omgservers.tester.operation.createTestVersion;

import com.omgservers.model.file.EncodedFileModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.tester.component.DeveloperApiTester;
import com.omgservers.tester.model.TestVersionModel;
import com.omgservers.tester.operation.getLuaFile.GetLuaFileOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTestVersionOperationImpl implements CreateTestVersionOperation {

    final GetLuaFileOperation getLuaFileOperation;

    final DeveloperApiTester developerApiTester;

    @Override
    public Long createTestVersion(final TestVersionModel testVersion,
                                  final String mainLua) throws IOException {
        return createTestVersion(testVersion,
                mainLua,
                VersionConfigModel.create());
    }

    @Override
    public Long createTestVersion(final TestVersionModel testVersion,
                                  final String mainLua,
                                  final VersionConfigModel versionConfig) throws IOException {
        final var newSourceCode = VersionSourceCodeModel.create();
        newSourceCode.getFiles().add(new EncodedFileModel("main.lua", Base64.getEncoder()
                .encodeToString(mainLua.getBytes(StandardCharsets.UTF_8))));
        newSourceCode.getFiles().add(new EncodedFileModel("omgservers.lua", Base64.getEncoder()
                .encodeToString(getLuaFileOperation.getOmgserversLua().getBytes(StandardCharsets.UTF_8))));

        final var createVersionResponse = developerApiTester.createVersion(testVersion.getDeveloperToken(),
                testVersion.getTenantId(),
                testVersion.getStageId(),
                versionConfig,
                newSourceCode
        );

        return createVersionResponse.getId();
    }
}

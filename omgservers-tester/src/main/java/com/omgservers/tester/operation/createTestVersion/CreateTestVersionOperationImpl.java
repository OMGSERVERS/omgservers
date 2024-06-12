package com.omgservers.tester.operation.createTestVersion;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.tester.component.DeveloperApiTester;
import com.omgservers.tester.model.TestVersionModel;
import com.omgservers.tester.operation.createBase64Archive.CreateBase64ArchiveOperation;
import com.omgservers.tester.operation.getLuaFile.GetLuaFileOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTestVersionOperationImpl implements CreateTestVersionOperation {

    final CreateBase64ArchiveOperation createBase64ArchiveOperation;
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
        final var base64Archive = createBase64ArchiveOperation.createBase64Archive(Map.of(
                        "main.lua", mainLua,
                        "omgservers.lua", getLuaFileOperation.getOmgserversLua()
                )
        );

        final var createVersionResponse = developerApiTester.createVersion(testVersion.getDeveloperToken(),
                testVersion.getTenantId(),
                testVersion.getStageId(),
                versionConfig,
                base64Archive
        );

        return createVersionResponse.getId();
    }
}

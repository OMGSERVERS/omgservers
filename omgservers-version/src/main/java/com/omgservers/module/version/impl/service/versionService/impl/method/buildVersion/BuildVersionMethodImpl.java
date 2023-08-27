package com.omgservers.module.version.impl.service.versionService.impl.method.buildVersion;

import com.omgservers.dto.version.SyncVersionShardedRequest;
import com.omgservers.model.version.VersionModel;
import com.omgservers.module.version.impl.operation.compileVersionSourceCode.CompileVersionSourceCodeOperation;
import com.omgservers.dto.version.BuildVersionRequest;
import com.omgservers.dto.version.BuildVersionResponse;
import com.omgservers.module.version.impl.service.versionShardedService.VersionShardedService;
import com.omgservers.module.version.factory.VersionModelFactory;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class BuildVersionMethodImpl implements BuildVersionMethod {

    final VersionShardedService versionShardedService;

    final CompileVersionSourceCodeOperation compileVersionCodeOperation;
    final VersionModelFactory versionModelFactory;
    final GenerateIdOperation generateIdOperation;

    @Override
    public Uni<BuildVersionResponse> buildVersion(BuildVersionRequest request) {
        BuildVersionRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var stageConfig = request.getStageConfig();
        final var sourceCode = request.getSourceCode();
        return compileVersionCodeOperation.compileVersionSourceCode(sourceCode)
                .flatMap(bytecode -> {
                    final var version = versionModelFactory.create(tenantId, stageId, stageConfig, sourceCode, bytecode);
                    return createVersion(version);
                })
                .map(BuildVersionResponse::new);
    }

    Uni<VersionModel> createVersion(final VersionModel version) {
        final var syncVersionInternalRequest = new SyncVersionShardedRequest(version);
        return versionShardedService.syncVersion(syncVersionInternalRequest)
                .replaceWith(version);
    }
}

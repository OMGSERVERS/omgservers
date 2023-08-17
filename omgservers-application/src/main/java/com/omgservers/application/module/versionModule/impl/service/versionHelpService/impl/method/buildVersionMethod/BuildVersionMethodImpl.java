package com.omgservers.application.module.versionModule.impl.service.versionHelpService.impl.method.buildVersionMethod;

import com.omgservers.application.module.versionModule.impl.operation.compileVersionSourceCodeOperation.CompileVersionSourceCodeOperation;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.VersionInternalService;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.request.BuildVersionHelpRequest;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.response.BuildVersionHelpResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.SyncVersionInternalRequest;
import com.omgservers.application.module.versionModule.model.VersionModel;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.CreateVersionInternalRequest;
import com.omgservers.application.module.versionModule.model.VersionModelFactory;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class BuildVersionMethodImpl implements BuildVersionMethod {

    final VersionInternalService versionInternalService;

    final CompileVersionSourceCodeOperation compileVersionCodeOperation;
    final VersionModelFactory versionModelFactory;
    final GenerateIdOperation generateIdOperation;

    @Override
    public Uni<BuildVersionHelpResponse> buildVersion(BuildVersionHelpRequest request) {
        BuildVersionHelpRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var stageConfig = request.getStageConfig();
        final var sourceCode = request.getSourceCode();
        return compileVersionCodeOperation.compileVersionSourceCode(sourceCode)
                .flatMap(bytecode -> {
                    final var version = versionModelFactory.create(tenantId, stageId, stageConfig, sourceCode, bytecode);
                    return createVersion(version);
                })
                .map(BuildVersionHelpResponse::new);
    }

    Uni<VersionModel> createVersion(final VersionModel version) {
        final var syncVersionInternalRequest = new SyncVersionInternalRequest(version);
        return versionInternalService.syncVersion(syncVersionInternalRequest)
                .replaceWith(version);
    }
}

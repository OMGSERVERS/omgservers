package com.omgservers.application.module.versionModule.impl.service.versionHelpService.impl.method.buildVersionMethod;

import com.omgservers.application.module.versionModule.impl.operation.compileVersionSourceCodeOperation.CompileVersionSourceCodeOperation;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.VersionInternalService;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.request.BuildVersionHelpRequest;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.response.BuildVersionHelpResponse;
import com.omgservers.application.module.versionModule.model.VersionModel;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.CreateVersionInternalRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class BuildVersionMethodImpl implements BuildVersionMethod {

    final VersionInternalService versionInternalService;
    final CompileVersionSourceCodeOperation compileVersionCodeOperation;

    @Override
    public Uni<BuildVersionHelpResponse> buildVersion(BuildVersionHelpRequest request) {
        BuildVersionHelpRequest.validate(request);

        final var tenant = request.getTenant();
        final var stage = request.getStage();
        final var stageConfig = request.getStageConfig();
        final var sourceCode = request.getSourceCode();
        return compileVersionCodeOperation.compileVersionSourceCode(sourceCode)
                .flatMap(bytecode -> {
                    final var uuid = UUID.randomUUID();
                    final var version = VersionModel.create(uuid, tenant, stage, stageConfig, sourceCode, bytecode);
                    return createVersion(version);
                })
                .map(BuildVersionHelpResponse::new);
    }

    Uni<VersionModel> createVersion(final VersionModel version) {
        final var createVersionServiceRequest = new CreateVersionInternalRequest(version);
        return versionInternalService.createVersion(createVersionServiceRequest)
                .replaceWith(version);
    }
}

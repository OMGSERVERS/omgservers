package com.omgservers.module.tenant.impl.service.versionService.impl.method.buildVersion;

import com.omgservers.dto.tenant.BuildVersionRequest;
import com.omgservers.dto.tenant.BuildVersionResponse;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.tenant.factory.VersionModelFactory;
import com.omgservers.module.tenant.impl.operation.compileVersionSourceCode.CompileVersionSourceCodeOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class BuildVersionMethodImpl implements BuildVersionMethod {

    final TenantModule tenantModule;

    final CompileVersionSourceCodeOperation compileVersionCodeOperation;
    final GenerateIdOperation generateIdOperation;

    final VersionModelFactory versionModelFactory;

    @Override
    public Uni<BuildVersionResponse> buildVersion(BuildVersionRequest request) {
        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var stageConfig = request.getVersionConfig();
        final var sourceCode = request.getSourceCode();
        return compileVersionCodeOperation.compileVersionSourceCode(sourceCode)
                .map(bytecode -> {
                    final var version = versionModelFactory.create(
                            tenantId,
                            stageId,
                            stageConfig,
                            sourceCode,
                            bytecode);
                    return version;
                })
                .map(BuildVersionResponse::new);
    }
}

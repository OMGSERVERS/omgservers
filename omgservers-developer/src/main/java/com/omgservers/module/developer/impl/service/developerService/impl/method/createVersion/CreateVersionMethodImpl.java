package com.omgservers.module.developer.impl.service.developerService.impl.method.createVersion;

import com.omgservers.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionShardedResponse;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.exception.ServerSideForbiddenException;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.version.VersionModule;
import com.omgservers.dto.version.BuildVersionRequest;
import com.omgservers.dto.version.BuildVersionResponse;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateVersionMethodImpl implements CreateVersionMethod {

    final VersionModule versionModule;
    final TenantModule tenantModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateVersionDeveloperResponse> createVersion(final CreateVersionDeveloperRequest request) {
        CreateVersionDeveloperRequest.validate(request);

        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var stageConfig = request.getStageConfig();
        final var sourceCode = request.getSourceCode();

        return checkCreateVersionPermission(tenantId, stageId, userId)
                .call(voidItem -> checkStage(tenantId, stageId))
                .flatMap(voidItem -> createVersion(tenantId, stageId, stageConfig, sourceCode))
                .map(VersionModel::getId)
                .map(CreateVersionDeveloperResponse::new);
    }

    Uni<Void> checkCreateVersionPermission(final Long tenantId,
                                           final Long stageId,
                                           final Long userId) {
        final var permission = StagePermissionEnum.CREATE_VERSION;
        final var hasStagePermissionServiceRequest =
                new HasStagePermissionShardedRequest(tenantId, stageId, userId, permission);
        return tenantModule.getStageShardedService().hasStagePermission(hasStagePermissionServiceRequest)
                .map(HasStagePermissionShardedResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                "tenant=%s, stage=%s, user=%s, permission=%s", tenantId, stageId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> checkStage(final Long tenantId, final Long stageId) {
        final var getStageServiceRequest = new GetStageShardedRequest(tenantId, stageId);
        return tenantModule.getStageShardedService().getStage(getStageServiceRequest)
                .map(GetStageShardedResponse::getStage)
                .replaceWithVoid();
    }

    Uni<VersionModel> createVersion(final Long tenantId,
                                    final Long stageId,
                                    final VersionStageConfigModel stageConfig,
                                    final VersionSourceCodeModel sourceCode) {
        final var createVersionHelpRequest = new BuildVersionRequest(tenantId, stageId, stageConfig, sourceCode);
        return versionModule.getVersionService().buildVersion(createVersionHelpRequest)
                .map(BuildVersionResponse::getVersion);
    }
}

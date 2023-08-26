package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createVersionMethod;

import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.request.BuildVersionHelpRequest;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.response.BuildVersionHelpResponse;
import com.omgservers.dto.developerModule.CreateVersionDeveloperRequest;
import com.omgservers.dto.developerModule.CreateVersionDeveloperResponse;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetStageShardRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionShardRequest;
import com.omgservers.exception.ServerSideForbiddenException;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.module.tenant.TenantModule;
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
                new HasStagePermissionShardRequest(tenantId, stageId, userId, permission);
        return tenantModule.getStageShardedService().hasStagePermission(hasStagePermissionServiceRequest)
                .map(HasStagePermissionInternalResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                "tenant=%s, stage=%s, user=%s, permission=%s", tenantId, stageId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> checkStage(final Long tenantId, final Long stageId) {
        final var getStageServiceRequest = new GetStageShardRequest(tenantId, stageId);
        return tenantModule.getStageShardedService().getStage(getStageServiceRequest)
                .map(GetStageInternalResponse::getStage)
                .replaceWithVoid();
    }

    Uni<VersionModel> createVersion(final Long tenantId,
                                    final Long stageId,
                                    final VersionStageConfigModel stageConfig,
                                    final VersionSourceCodeModel sourceCode) {
        final var createVersionHelpRequest = new BuildVersionHelpRequest(tenantId, stageId, stageConfig, sourceCode);
        return versionModule.getVersionHelpService().buildVersion(createVersionHelpRequest)
                .map(BuildVersionHelpResponse::getVersion);
    }
}

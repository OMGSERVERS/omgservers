package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createVersionDeveloperMethod;

import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.request.BuildVersionHelpRequest;
import com.omgservers.application.module.versionModule.impl.service.versionHelpService.response.BuildVersionHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateVersionHelpResponse;
import com.omgservers.application.module.tenantModule.model.stage.StagePermissionEnum;
import com.omgservers.application.module.versionModule.model.VersionModel;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateVersionHelpRequest;
import com.omgservers.application.module.versionModule.model.VersionSourceCodeModel;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import com.omgservers.application.exception.ServerSideForbiddenException;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.GetStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.HasStagePermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.HasStagePermissionInternalResponse;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateVersionMethodImpl implements CreateVersionMethod {

    final VersionModule versionModule;
    final TenantModule tenantModule;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateVersionHelpResponse> createVersion(final CreateVersionHelpRequest request) {
        CreateVersionHelpRequest.validate(request);

        final var user = securityIdentity.<UUID>getAttribute("uuid");
        final var tenant = request.getTenant();
        final var stageUuid = request.getStage();
        final var stageConfig = request.getStageConfig();
        final var sourceCode = request.getSourceCode();

        return checkCreateVersionPermission(tenant, stageUuid, user)
                .call(voidItem -> checkStage(tenant, stageUuid))
                .flatMap(voidItem -> createVersion(tenant, stageUuid, stageConfig, sourceCode))
                .map(VersionModel::getUuid)
                .map(CreateVersionHelpResponse::new);
    }

    Uni<Void> checkCreateVersionPermission(final UUID tenant,
                                           final UUID stage,
                                           final UUID user) {
        final var permission = StagePermissionEnum.CREATE_VERSION;
        final var hasStagePermissionServiceRequest = new HasStagePermissionInternalRequest(tenant, stage, user, permission);
        return tenantModule.getStageInternalService().hasStagePermission(hasStagePermissionServiceRequest)
                .map(HasStagePermissionInternalResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                "tenant=%s, stage=%s, user=%s, permission=%s", tenant, stage, user, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<Void> checkStage(final UUID tenant, final UUID stage) {
        final var getStageServiceRequest = new GetStageInternalRequest(tenant, stage);
        return tenantModule.getStageInternalService().getStage(getStageServiceRequest)
                .map(GetStageInternalResponse::getStage)
                .replaceWithVoid();
    }

    Uni<VersionModel> createVersion(final UUID tenant,
                                    final UUID stage,
                                    final VersionStageConfigModel stageConfig,
                                    final VersionSourceCodeModel sourceCode) {
        final var createVersionHelpRequest = new BuildVersionHelpRequest(tenant, stage, stageConfig, sourceCode);
        return versionModule.getVersionHelpService().buildVersion(createVersionHelpRequest)
                .map(BuildVersionHelpResponse::getVersion);
    }
}

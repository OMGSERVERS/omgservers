package com.omgservers.module.developer.impl.service.developerService.impl.method.createVersion;

import com.omgservers.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.dto.tenant.BuildVersionRequest;
import com.omgservers.dto.tenant.BuildVersionResponse;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionShardedResponse;
import com.omgservers.dto.tenant.SyncVersionShardedRequest;
import com.omgservers.exception.ServerSideForbiddenException;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.tenant.factory.VersionModelFactory;
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

    final TenantModule tenantModule;

    final VersionModelFactory versionModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateVersionDeveloperResponse> createVersion(final CreateVersionDeveloperRequest request) {
        CreateVersionDeveloperRequest.validate(request);

        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var stageConfig = request.getVersionConfig();
        final var sourceCode = request.getSourceCode();

        return checkCreateVersionPermission(tenantId, stageId, userId)
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

    Uni<VersionModel> createVersion(final Long tenantId,
                                    final Long stageId,
                                    final VersionConfigModel versionConfig,
                                    final VersionSourceCodeModel sourceCode) {
        //TODO: redesign in more async way
        final var buildVersionRequest = new BuildVersionRequest(stageId, versionConfig, sourceCode);
        return tenantModule.getVersionService().buildVersion(buildVersionRequest)
                .map(BuildVersionResponse::getVersion)
                .flatMap(version -> {
                    final var syncVersionInternalRequest = new SyncVersionShardedRequest(tenantId, version);
                    return tenantModule.getVersionShardedService().syncVersion(syncVersionInternalRequest)
                            .replaceWith(version);
                });
    }
}

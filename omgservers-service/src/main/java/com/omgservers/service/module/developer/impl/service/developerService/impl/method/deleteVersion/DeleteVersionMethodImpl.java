package com.omgservers.service.module.developer.impl.service.developerService.impl.method.deleteVersion;

import com.omgservers.model.dto.developer.DeleteVersionDeveloperRequest;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.HasStagePermissionRequest;
import com.omgservers.model.dto.tenant.HasStagePermissionResponse;
import com.omgservers.model.stagePermission.StagePermissionEnum;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.exception.ServerSideForbiddenException;
import com.omgservers.service.factory.VersionModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteVersionMethodImpl implements DeleteVersionMethod {

    final TenantModule tenantModule;

    final VersionModelFactory versionModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteVersionDeveloperResponse> deleteVersion(final DeleteVersionDeveloperRequest request) {
        final var userId = securityIdentity.<Long>getAttribute("userId");
        final var tenantId = request.getTenantId();
        final var versionId = request.getId();

        return getVersion(tenantId, versionId)
                .flatMap(version -> {
                    final var stageId = version.getStageId();

                    return checkVersionManagementPermission(tenantId, stageId, userId)
                            .flatMap(voidItem -> deleteVersion(tenantId, versionId))
                            .map(DeleteVersionDeveloperResponse::new);
                });
    }

    Uni<VersionModel> getVersion(Long tenantId, Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<Void> checkVersionManagementPermission(final Long tenantId,
                                               final Long stageId,
                                               final Long userId) {
        final var permission = StagePermissionEnum.VERSION_MANAGEMENT;
        final var hasStagePermissionServiceRequest =
                new HasStagePermissionRequest(tenantId, stageId, userId, permission);
        return tenantModule.getStageService().hasStagePermission(hasStagePermissionServiceRequest)
                .map(HasStagePermissionResponse::getResult)
                .invoke(result -> {
                    if (!result) {
                        throw new ServerSideForbiddenException(String.format("lack of permission, " +
                                "tenant=%s, stage=%s, user=%s, permission=%s", tenantId, stageId, userId, permission));
                    }
                })
                .replaceWithVoid();
    }

    Uni<Boolean> deleteVersion(final Long tenantId,
                               final Long id) {
        final var request = new DeleteVersionRequest(tenantId, id);
        return tenantModule.getVersionService().deleteVersion(request)
                .map(DeleteVersionResponse::getDeleted);
    }
}

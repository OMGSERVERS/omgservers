package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateLobbyRequestDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateLobbyRequestDeveloperResponse;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.SyncTenantLobbyRequestResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantStagePermissionOperation;
import com.omgservers.service.factory.tenant.TenantLobbyRequestModelFactory;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateLobbyRequestMethodImpl implements CreateLobbyRequestMethod {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final CheckTenantStagePermissionOperation checkTenantStagePermissionOperation;

    final TenantLobbyRequestModelFactory tenantLobbyRequestModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateLobbyRequestDeveloperResponse> execute(final CreateLobbyRequestDeveloperRequest request) {
        log.info("Create lobby request, request={}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var deploymentId = request.getDeploymentId();

        return getTenantDeployment(tenantId, deploymentId)
                .flatMap(tenantDeployment -> {
                    final var stageId = tenantDeployment.getStageId();

                    final var permissionQualifier =
                            TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGEMENT;
                    return checkTenantStagePermissionOperation.execute(tenantId,
                                    stageId,
                                    userId,
                                    permissionQualifier)
                            .flatMap(voidItem -> createTenantLobbyRequest(tenantId, deploymentId));
                })
                .replaceWith(new CreateLobbyRequestDeveloperResponse());
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<Boolean> createTenantLobbyRequest(final Long tenantId,
                                          final Long deploymentId) {
        final var tenantLobbyRequest = tenantLobbyRequestModelFactory.create(tenantId,
                deploymentId);
        final var request = new SyncTenantLobbyRequestRequest(tenantLobbyRequest);
        return tenantModule.getService().syncTenantLobbyRequestWithIdempotency(request)
                .map(SyncTenantLobbyRequestResponse::getCreated);
    }

}
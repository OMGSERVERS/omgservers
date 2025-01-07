package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateMatchmakerRequestDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateMatchmakerRequestDeveloperResponse;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.SyncTenantMatchmakerRequestResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantStagePermissionOperation;
import com.omgservers.service.factory.tenant.TenantMatchmakerRequestModelFactory;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.getIdByTenant.GetIdByTenantOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateMatchmakerRequestMethodImpl implements CreateMatchmakerRequestMethod {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final CheckTenantStagePermissionOperation checkTenantStagePermissionOperation;
    final GetIdByTenantOperation getIdByTenantOperation;

    final TenantMatchmakerRequestModelFactory tenantMatchmakerRequestModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateMatchmakerRequestDeveloperResponse> execute(
            final CreateMatchmakerRequestDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> {
                    final var deploymentId = request.getDeploymentId();
                    return getTenantDeployment(tenantId, deploymentId)
                            .flatMap(tenantDeployment -> {
                                final var stageId = tenantDeployment.getStageId();

                                final var permissionQualifier =
                                        TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER;
                                return checkTenantStagePermissionOperation.execute(tenantId,
                                                stageId,
                                                userId,
                                                permissionQualifier)
                                        .invoke(voidItem -> log.info(
                                                "A new matchmaker was requested for deployment \"{}\" in tenant \"{}\" by the user {}",
                                                deploymentId, tenantId, userId))
                                        .flatMap(voidItem -> createTenantMatchmakerRequest(tenantId, deploymentId));
                            });
                })
                .replaceWith(new CreateMatchmakerRequestDeveloperResponse());
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<Boolean> createTenantMatchmakerRequest(final Long tenantId,
                                               final Long tenantDeploymentId) {
        final var tenantMatchmakerRequest = tenantMatchmakerRequestModelFactory
                .create(tenantId, tenantDeploymentId);
        final var request = new SyncTenantMatchmakerRequestRequest(tenantMatchmakerRequest);
        return tenantModule.getService().syncTenantMatchmakerRequestWithIdempotency(request)
                .map(SyncTenantMatchmakerRequestResponse::getCreated);
    }

}

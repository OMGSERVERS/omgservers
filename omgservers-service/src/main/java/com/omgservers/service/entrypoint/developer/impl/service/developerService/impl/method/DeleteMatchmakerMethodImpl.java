package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.DeleteMatchmakerDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteMatchmakerDeveloperResponse;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.module.matchmaker.GetMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.operation.CheckTenantStagePermissionOperation;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
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
class DeleteMatchmakerMethodImpl implements DeleteMatchmakerMethod {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    final CheckTenantStagePermissionOperation checkTenantStagePermissionOperation;

    final TenantVersionModelFactory tenantVersionModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<DeleteMatchmakerDeveloperResponse> execute(final DeleteMatchmakerDeveloperRequest request) {
        log.info("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId = securityIdentity
                .<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var matchmakerId = request.getMatchmakerId();

        return getMatchmaker(matchmakerId)
                .flatMap(matchmaker -> {
                    final var tenantId = matchmaker.getTenantId();
                    final var tenantDeploymentId = matchmaker.getDeploymentId();
                    return getTenantDeployment(tenantId, tenantDeploymentId)
                            .flatMap(tenantDeployment -> {
                                final var tenantStageId = tenantDeployment.getStageId();

                                final var permissionQualifier =
                                        TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGEMENT;
                                return checkTenantStagePermissionOperation.execute(tenantId,
                                                tenantStageId,
                                                userId,
                                                permissionQualifier)
                                        .flatMap(voidItem -> deleteMatchmaker(matchmakerId))
                                        .map(DeleteMatchmakerDeveloperResponse::new);
                            });
                });
    }

    Uni<MatchmakerModel> getMatchmaker(final Long matchmakerId) {
        final var request = new GetMatchmakerRequest(matchmakerId);
        return matchmakerModule.getService().getMatchmaker(request)
                .map(GetMatchmakerResponse::getMatchmaker);
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<Boolean> deleteMatchmaker(final Long matchmakerId) {
        final var request = new DeleteMatchmakerRequest(matchmakerId);
        return matchmakerModule.getService().deleteMatchmaker(request)
                .map(DeleteMatchmakerResponse::getDeleted);
    }
}

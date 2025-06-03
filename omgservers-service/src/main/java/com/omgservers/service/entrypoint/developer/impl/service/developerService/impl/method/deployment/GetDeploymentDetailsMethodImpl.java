package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployment;

import com.omgservers.schema.entrypoint.developer.GetDeploymentDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetDeploymentDetailsDeveloperResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.authz.AuthorizeDeploymentRequestOperation;
import com.omgservers.service.operation.security.GetSecurityAttributeOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetDeploymentDetailsMethodImpl implements GetDeploymentDetailsMethod {

    final AuthorizeDeploymentRequestOperation authorizeDeploymentRequestOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    @Override
    public Uni<GetDeploymentDetailsDeveloperResponse> execute(final GetDeploymentDetailsDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var deploymentId = request.getDeploymentId();
        final var userId = getSecurityAttributeOperation.getUserId();
        final var permission = TenantStagePermissionQualifierEnum.DEPLOYMENT_MANAGER;
        return authorizeDeploymentRequestOperation.execute(deploymentId, userId, permission)
                .flatMap(authorization -> {
                    final var tenantId = authorization.tenantId();
                    throw new ServerSideConflictException(ExceptionQualifierEnum.UNSUPPORTED_FEATURE,
                            "unsupported feature");
                });
    }
}

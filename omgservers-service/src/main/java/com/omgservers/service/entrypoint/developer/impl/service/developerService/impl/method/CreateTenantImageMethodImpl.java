package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateImageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateImageDeveloperResponse;
import com.omgservers.schema.model.tenantImage.TenantImageConfigDto;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.shard.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.shard.tenant.tenantImage.SyncTenantImageResponse;
import com.omgservers.service.factory.tenant.TenantImageModelFactory;
import com.omgservers.service.operation.authz.AuthorizeDockerImageOperation;
import com.omgservers.service.operation.authz.AuthorizeTenantVersionRequestOperation;
import com.omgservers.service.operation.authz.TenantVersionAuthorization;
import com.omgservers.service.operation.security.GetSecurityAttributeOperation;
import com.omgservers.service.shard.lobby.LobbyShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateTenantImageMethodImpl implements CreateTenantImageMethod {

    final TenantShard tenantShard;
    final LobbyShard lobbyShard;

    final AuthorizeTenantVersionRequestOperation authorizeTenantVersionRequestOperation;
    final AuthorizeDockerImageOperation authorizeDockerImageOperation;
    final GetSecurityAttributeOperation getSecurityAttributeOperation;

    final TenantImageModelFactory tenantImageModelFactory;

    @Override
    public Uni<CreateImageDeveloperResponse> execute(final CreateImageDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var project = request.getProject();
        final var version = request.getVersion();
        final var userId = getSecurityAttributeOperation.getUserId();
        final var permission = TenantProjectPermissionQualifierEnum.VERSION_MANAGER;

        return authorizeTenantVersionRequestOperation.execute(tenant, project, version, userId, permission)
                .flatMap(authorization -> {
                    final var image = request.getImage();
                    authorizeDockerImageOperation.execute(tenant, project, image);

                    final var qualifier = request.getQualifier();
                    return createTenantImage(authorization, qualifier, image)
                            .invoke(created -> {
                                if (created) {
                                    log.info("Created new image in tenant \"{}\"", tenant);
                                }
                            })
                            .map(CreateImageDeveloperResponse::new);
                });
    }

    Uni<Boolean> createTenantImage(final TenantVersionAuthorization authorization,
                                   final TenantImageQualifierEnum qualifier,
                                   final String image) {
        final var tenantId = authorization.tenantId();
        final var tenantVersionId = authorization.tenantVersionId();

        final var idempotencyKey = tenantVersionId + "/" + qualifier;
        final var tenantImage = tenantImageModelFactory.create(tenantId,
                tenantVersionId,
                qualifier,
                image,
                TenantImageConfigDto.create(),
                idempotencyKey);
        final var request = new SyncTenantImageRequest(tenantImage);
        return tenantShard.getService().executeWithIdempotency(request)
                .map(SyncTenantImageResponse::getCreated);
    }
}

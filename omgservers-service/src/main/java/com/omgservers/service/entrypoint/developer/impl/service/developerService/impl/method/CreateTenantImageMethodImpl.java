package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method;

import com.omgservers.schema.entrypoint.developer.CreateTenantImageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantImageDeveloperResponse;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageResponse;
import com.omgservers.service.factory.tenant.TenantImageModelFactory;
import com.omgservers.service.operation.authz.AuthorizeDockerImageOperation;
import com.omgservers.service.operation.authz.AuthorizeTenantVersionRequestOperation;
import com.omgservers.service.operation.authz.TenantVersionAuthorization;
import com.omgservers.service.operation.docker.ParseDockerImageOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import com.omgservers.service.shard.lobby.LobbyShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.quarkus.security.identity.SecurityIdentity;
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
    final ParseDockerImageOperation parseDockerImageOperation;

    final TenantImageModelFactory tenantImageModelFactory;
    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateTenantImageDeveloperResponse> execute(final CreateTenantImageDeveloperRequest request) {
        log.info("Requested, {}", request);

        final var tenant = request.getTenant();
        final var project = request.getProject();
        final var version = request.getVersion();
        final var userId = securityIdentity.<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());
        final var permission = TenantProjectPermissionQualifierEnum.VERSION_MANAGER;

        return authorizeTenantVersionRequestOperation.execute(tenant, project, version, userId, permission)
                .flatMap(tenantVersionAuthorization -> {
                    final var image = request.getImage();
                    authorizeDockerImageOperation.execute(tenant, project, image);

                    final var qualifier = request.getQualifier();
                    return createTenantImage(tenantVersionAuthorization, qualifier, image)
                            .invoke(created -> {
                                if (created) {
                                    log.info("A new image was created in tenant \"{}\"", tenant);
                                }
                            })
                            .map(CreateTenantImageDeveloperResponse::new);
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
                idempotencyKey);
        final var request = new SyncTenantImageRequest(tenantImage);
        return tenantShard.getService().syncTenantImageWithIdempotency(request)
                .map(SyncTenantImageResponse::getCreated);
    }
}

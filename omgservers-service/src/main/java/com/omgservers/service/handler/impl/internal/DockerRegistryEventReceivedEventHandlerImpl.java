package com.omgservers.service.handler.impl.internal;

import com.omgservers.schema.entrypoint.registry.handleEvents.DockerRegistryEventDto;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageRequest;
import com.omgservers.schema.module.tenant.tenantImage.SyncTenantImageResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.DockerRegistryEventReceivedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.tenant.TenantImageModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.buildDockerImageId.BuildDockerImageIdOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.operation.parseDockerRepository.ParseDockerRepositoryOperation;
import com.omgservers.service.service.registry.dto.DockerRegistryContainerQualifierEnum;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DockerRegistryEventReceivedEventHandlerImpl implements EventHandler {

    final String PUSH_ACTION = "push";

    final TenantModule tenantModule;

    final ParseDockerRepositoryOperation parseDockerRepositoryOperation;
    final BuildDockerImageIdOperation buildDockerImageIdOperation;
    final GetConfigOperation getConfigOperation;

    final TenantImageModelFactory tenantImageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DOCKER_REGISTRY_EVENT_RECEIVED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (DockerRegistryEventReceivedEventBodyModel) event.getBody();
        final var dockerRegistryEvent = body.getEvent();
        final var action = dockerRegistryEvent.getAction();

        final var idempotencyKey = event.getIdempotencyKey();

        return switch (action) {
            case PUSH_ACTION -> handlePushAction(dockerRegistryEvent, idempotencyKey);
            default -> {
                log.info("Received uninteresting action, skip handling, action={}", action);
                yield Uni.createFrom().voidItem();
            }
        };
    }

    Uni<Void> handlePushAction(final DockerRegistryEventDto event, final String idempotencyKey) {
        final var repositoryString = event.getTarget().getRepository();
        final var dockerRepository = parseDockerRepositoryOperation
                .parseDockerRegistryRepository(repositoryString);

        final var tag = event.getTarget().getTag();
        try {
            final var tenantId = dockerRepository.getTenantId();
            final var versionId = Long.valueOf(tag);
            final var registryHost = getRegistryHost(event);
            final var imageId = registryHost + "/" +
                    buildDockerImageIdOperation.buildDockerImageId(dockerRepository, versionId);

            return syncTenantImage(tenantId,
                    versionId,
                    imageId,
                    dockerRepository.getContainer(),
                    idempotencyKey)
                    .replaceWithVoid();
        } catch (NumberFormatException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_ARGUMENT, e.getMessage(), e);
        }
    }

    Uni<Boolean> syncTenantImage(final Long tenantId,
                                 final Long tenantVersionId,
                                 final String imageId,
                                 final DockerRegistryContainerQualifierEnum qualifier,
                                 final String idempotencyKey) {
        final var tenantImageQualifier = switch (qualifier) {
            case LOBBY -> TenantImageQualifierEnum.LOBBY;
            case MATCH -> TenantImageQualifierEnum.MATCH;
            case UNIVERSAL -> TenantImageQualifierEnum.UNIVERSAL;
        };

        final var tenantImage = tenantImageModelFactory.create(tenantId,
                tenantVersionId,
                tenantImageQualifier,
                imageId,
                idempotencyKey);

        final var request = new SyncTenantImageRequest(tenantImage);
        return tenantModule.getTenantService().syncTenantImageWithIdempotency(request)
                .map(SyncTenantImageResponse::getCreated);
    }

    String getRegistryHost(final DockerRegistryEventDto event) {
        try {
            final var uri = URI.create(event.getTarget().getUrl());
            final var port = uri.getPort();
            final var host = uri.getHost();
            if (port == -1) {
                return host;
            } else {
                return host + ":" + port;
            }
        } catch (Exception e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT,
                    "target url couldn't be parsed");
        }
    }
}
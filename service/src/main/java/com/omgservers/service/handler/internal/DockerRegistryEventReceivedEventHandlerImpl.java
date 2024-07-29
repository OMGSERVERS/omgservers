package com.omgservers.service.handler.internal;

import com.omgservers.schema.service.registry.DockerRegistryContainerQualifierEnum;
import com.omgservers.schema.module.tenant.versionImageRef.SyncVersionImageRefRequest;
import com.omgservers.schema.module.tenant.versionImageRef.SyncVersionImageRefResponse;
import com.omgservers.schema.event.EventModel;
import com.omgservers.schema.event.EventQualifierEnum;
import com.omgservers.schema.event.body.internal.DockerRegistryEventReceivedEventBodyModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.versionImageRef.VersionImageRefQualifierEnum;
import com.omgservers.schema.entrypoint.registry.handleEvents.DockerRegistryEventDto;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.tenant.VersionImageRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.buildDockerImageId.BuildDockerImageIdOperation;
import com.omgservers.service.operation.parseDockerRepository.ParseDockerRepositoryOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DockerRegistryEventReceivedEventHandlerImpl implements EventHandler {

    final String PUSH_ACTION = "push";

    final TenantModule tenantModule;

    final ParseDockerRepositoryOperation parseDockerRepositoryOperation;
    final BuildDockerImageIdOperation buildDockerImageIdOperation;

    final VersionImageRefModelFactory versionImageRefModelFactory;

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
            final var imageId = buildDockerImageIdOperation.buildDockerImageId(dockerRepository, versionId);

            return syncVersionImageRef(tenantId,
                    versionId,
                    imageId,
                    dockerRepository.getContainer(),
                    idempotencyKey)
                    .replaceWithVoid();
        } catch (NumberFormatException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.ARGUMENT_WRONG, e.getMessage(), e);
        }
    }

    Uni<Boolean> syncVersionImageRef(final Long tenantId,
                                     final Long versionId,
                                     final String imageId,
                                     final DockerRegistryContainerQualifierEnum qualifier,
                                     final String idempotencyKey) {
        final var versionImageRefQualifier = switch (qualifier) {
            case LOBBY -> VersionImageRefQualifierEnum.LOBBY;
            case MATCH -> VersionImageRefQualifierEnum.MATCH;
            case UNIVERSAL -> VersionImageRefQualifierEnum.UNIVERSAL;
        };

        final var versionImageRef = versionImageRefModelFactory.create(tenantId,
                versionId,
                versionImageRefQualifier,
                imageId,
                idempotencyKey);

        final var request = new SyncVersionImageRefRequest(versionImageRef);
        return tenantModule.getVersionService().syncVersionImageRefWithIdempotency(request)
                .map(SyncVersionImageRefResponse::getCreated);
    }
}

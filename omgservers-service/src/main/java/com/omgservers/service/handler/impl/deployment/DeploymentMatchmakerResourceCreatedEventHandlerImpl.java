package com.omgservers.service.handler.impl.deployment;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.matchmaker.MatchmakerConfigDto;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.GetDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.GetDeploymentMatchmakerResourceResponse;
import com.omgservers.schema.shard.matchmaker.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.SyncMatchmakerResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentMatchmakerResourceCreatedEventBodyModel;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeploymentMatchmakerResourceCreatedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final MatchmakerShard matchmakerShard;

    final MatchmakerModelFactory matchmakerModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DEPLOYMENT_MATCHMAKER_RESOURCE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (DeploymentMatchmakerResourceCreatedEventBodyModel) event.getBody();
        final var deploymentId = body.getDeploymentId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getDeploymentMatchmakerResource(deploymentId, id)
                .flatMap(deploymentMatchmakerResource -> {
                    log.debug("Created, {}", deploymentMatchmakerResource);

                    return createMatchmaker(deploymentMatchmakerResource, idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<DeploymentMatchmakerResourceModel> getDeploymentMatchmakerResource(final Long tenantId, final Long id) {
        final var request = new GetDeploymentMatchmakerResourceRequest(tenantId, id);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentMatchmakerResourceResponse::getDeploymentMatchmakerResource);
    }

    Uni<Boolean> createMatchmaker(final DeploymentMatchmakerResourceModel deploymentMatchmakerResource,
                                  final String idempotencyKey) {
        final var deploymentId = deploymentMatchmakerResource.getDeploymentId();
        final var matchmakerId = deploymentMatchmakerResource.getMatchmakerId();
        final var matchmaker = matchmakerModelFactory.create(matchmakerId,
                deploymentId,
                MatchmakerConfigDto.create(),
                idempotencyKey);
        final var request = new SyncMatchmakerRequest(matchmaker);
        return matchmakerShard.getService().executeWithIdempotency(request)
                .map(SyncMatchmakerResponse::getCreated);
    }
}

package com.omgservers.service.handler.impl.deployment;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.GetDeploymentMatchmakerResourceRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerResource.GetDeploymentMatchmakerResourceResponse;
import com.omgservers.schema.shard.matchmaker.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentMatchmakerResourceDeletedEventBodyModel;
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
public class DeploymentMatchmakerResourceDeletedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final MatchmakerShard matchmakerShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.DEPLOYMENT_MATCHMAKER_RESOURCE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (DeploymentMatchmakerResourceDeletedEventBodyModel) event.getBody();
        final var deploymentId = body.getDeploymentId();
        final var id = body.getId();

        return getDeploymentMatchmakerResource(deploymentId, id)
                .flatMap(deploymentMatchmakerResource -> {
                    log.debug("Deleted, {}", deploymentMatchmakerResource);

                    final var matchmakerId = deploymentMatchmakerResource.getMatchmakerId();
                    return deleteMatchmaker(matchmakerId);
                })
                .replaceWithVoid();
    }

    Uni<DeploymentMatchmakerResourceModel> getDeploymentMatchmakerResource(final Long tenantId, final Long id) {
        final var request = new GetDeploymentMatchmakerResourceRequest(tenantId, id);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentMatchmakerResourceResponse::getDeploymentMatchmakerResource);
    }

    Uni<Boolean> deleteMatchmaker(final Long matchmakerId) {
        final var request = new DeleteMatchmakerRequest(matchmakerId);
        return matchmakerShard.getService().execute(request)
                .map(DeleteMatchmakerResponse::getDeleted);
    }
}

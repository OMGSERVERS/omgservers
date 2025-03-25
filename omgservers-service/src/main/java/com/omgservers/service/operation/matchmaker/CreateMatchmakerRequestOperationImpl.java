package com.omgservers.service.operation.matchmaker;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestConfigDto;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.FindDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.FindDeploymentMatchmakerAssignmentResponse;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.SyncMatchmakerRequestRequest;
import com.omgservers.schema.module.matchmaker.matchmakerRequest.SyncMatchmakerRequestResponse;
import com.omgservers.service.factory.matchmaker.MatchmakerRequestModelFactory;
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
class CreateMatchmakerRequestOperationImpl implements CreateMatchmakerRequestOperation {

    final DeploymentShard deploymentShard;
    final MatchmakerShard matchmakerShard;

    final MatchmakerRequestModelFactory matchmakerRequestModelFactory;

    @Override
    public Uni<Boolean> execute(final Long deploymentId,
                                final Long clientId,
                                final String mode) {
        return findDeploymentMatchmakerAssignment(deploymentId, clientId)
                .map(DeploymentMatchmakerAssignmentModel::getMatchmakerId)
                .flatMap(matchmakerId -> {
                    final var matchmakerRequestConfig = new MatchmakerRequestConfigDto();
                    final var matchmakerRequest = matchmakerRequestModelFactory.create(matchmakerId,
                            clientId,
                            mode,
                            matchmakerRequestConfig);

                    return syncMatchmakerRequest(matchmakerRequest);
                })
                .onFailure()
                .recoverWithItem(t -> {
                    log.warn("Failed, clientId={}, {}:{}",
                            clientId,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Boolean.FALSE;
                });
    }

    Uni<DeploymentMatchmakerAssignmentModel> findDeploymentMatchmakerAssignment(final Long deploymentId,
                                                                                final Long clientId) {
        final var request = new FindDeploymentMatchmakerAssignmentRequest(deploymentId, clientId);
        return deploymentShard.getService().execute(request)
                .map(FindDeploymentMatchmakerAssignmentResponse::getDeploymentMatchmakerAssignment);
    }

    Uni<Boolean> syncMatchmakerRequest(final MatchmakerRequestModel matchmakerRequest) {
        final var request = new SyncMatchmakerRequestRequest(matchmakerRequest);
        return matchmakerShard.getService().executeWithIdempotency(request)
                .map(SyncMatchmakerRequestResponse::getCreated);
    }
}

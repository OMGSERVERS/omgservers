package com.omgservers.testDataFactory;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.match.MatchConfigDto;
import com.omgservers.schema.model.matchmaker.MatchmakerConfigDto;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentConfigDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceConfigDto;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestConfigDto;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.schema.shard.matchmaker.matchmaker.SyncMatchmakerRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchAssignment.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerMatchResource.SyncMatchmakerMatchResourceRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerRequest.SyncMatchmakerRequestRequest;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchAssignmentModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchResourceModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerRequestModelFactory;
import com.omgservers.service.shard.matchmaker.service.testInterface.MatchmakerServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerTestDataFactory {

    private static final String MODE = "mode";

    final MatchmakerServiceTestInterface matchmakerService;

    final MatchmakerMatchAssignmentModelFactory matchmakerMatchAssignmentModelFactory;
    final MatchmakerMatchResourceModelFactory matchmakerMatchResourceModelFactory;
    final MatchmakerRequestModelFactory matchmakerRequestModelFactory;
    final MatchmakerModelFactory matchmakerModelFactory;

    public MatchmakerModel createMatchmaker(final DeploymentMatchmakerResourceModel deploymentMatchmakerResource) {
        final var matchmakerId = deploymentMatchmakerResource.getMatchmakerId();
        final var deploymentId = deploymentMatchmakerResource.getDeploymentId();

        final var matchmaker = matchmakerModelFactory.create(matchmakerId, deploymentId, MatchmakerConfigDto.create());
        final var syncMatchmakerRequest = new SyncMatchmakerRequest(matchmaker);
        matchmakerService.execute(syncMatchmakerRequest);
        return matchmaker;
    }

    public MatchmakerRequestModel createMatchmakerRequest(final MatchmakerModel matchmaker,
                                                          final ClientModel client) {
        final var matchmakerId = matchmaker.getId();
        final var clientId = client.getId();

        final var config = new MatchmakerRequestConfigDto();
        final var matchmakerRequest = matchmakerRequestModelFactory.create(matchmakerId,
                clientId,
                MODE,
                config);

        final var syncMatchmakerRequestRequest = new SyncMatchmakerRequestRequest(matchmakerRequest);
        matchmakerService.execute(syncMatchmakerRequestRequest);
        return matchmakerRequest;

    }

    public MatchmakerMatchResourceModel createMatchmakerMatchResource(final MatchmakerModel matchmaker) {
        final var matchmakerId = matchmaker.getId();

        final var matchmakerMatchResource = matchmakerMatchResourceModelFactory.create(matchmakerId,
                MatchmakerMatchResourceConfigDto.create(MatchConfigDto.create("mode")));
        final var syncMatchmakerMatchRequest = new SyncMatchmakerMatchResourceRequest(matchmakerMatchResource);
        matchmakerService.execute(syncMatchmakerMatchRequest);
        return matchmakerMatchResource;
    }

    public MatchmakerMatchAssignmentModel createMatchmakerMatchAssignment(
            final MatchmakerMatchResourceModel matchmakerMatchResource,
            final MatchmakerRequestModel matchmakerRequest,
            final ClientModel client) {

        final var matchmakerId = matchmakerMatchResource.getMatchmakerId();
        final var matchId = matchmakerMatchResource.getMatchId();
        final var clientId = client.getId();

        final var config = MatchmakerMatchAssignmentConfigDto.create(matchmakerRequest);
        final var matchmakerMatchAssignment = matchmakerMatchAssignmentModelFactory.create(matchmakerId,
                matchId,
                clientId,
                "players",
                config);

        final var syncMatchmakerMatchAssignmentRequest = new SyncMatchmakerMatchAssignmentRequest(
                matchmakerMatchAssignment);
        matchmakerService.execute(syncMatchmakerMatchAssignmentRequest);
        return matchmakerMatchAssignment;
    }
}

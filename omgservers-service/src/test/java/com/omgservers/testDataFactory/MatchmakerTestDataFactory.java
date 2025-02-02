package com.omgservers.testDataFactory;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchConfigDto;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentConfigDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchAssignmentRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.schema.module.matchmaker.SyncMatchmakerRequest;
import com.omgservers.service.factory.matchmaker.MatchmakerAssignmentModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchAssignmentModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchRuntimeRefModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.testInterface.MatchmakerServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerTestDataFactory {

    final MatchmakerServiceTestInterface matchmakerService;

    final MatchmakerMatchRuntimeRefModelFactory matchmakerMatchRuntimeRefModelFactory;
    final MatchmakerMatchAssignmentModelFactory matchmakerMatchAssignmentModelFactory;
    final MatchmakerAssignmentModelFactory matchmakerAssignmentModelFactory;
    final MatchmakerMatchModelFactory matchmakerMatchModelFactory;
    final MatchmakerModelFactory matchmakerModelFactory;

    public MatchmakerModel createMatchmaker(final TenantModel tenant,
                                            final TenantDeploymentModel tenantDeployment) {
        final var tenantId = tenant.getId();
        final var tenantDeploymentId = tenantDeployment.getId();

        final var matchmaker = matchmakerModelFactory.create(tenantId, tenantDeploymentId);
        final var syncMatchmakerRequest = new SyncMatchmakerRequest(matchmaker);
        matchmakerService.execute(syncMatchmakerRequest);
        return matchmaker;
    }

    public MatchmakerAssignmentModel createMatchmakerAssignment(final MatchmakerModel matchmaker,
                                                                final ClientModel client) {
        final var matchmakerId = matchmaker.getId();
        final var clientId = client.getId();

        final var matchmakerAssignment = matchmakerAssignmentModelFactory.create(matchmakerId, clientId);
        final var syncMatchmakerAssignmentRequest = new SyncMatchmakerAssignmentRequest(matchmakerAssignment);
        matchmakerService.execute(syncMatchmakerAssignmentRequest);
        return matchmakerAssignment;
    }

    public MatchmakerMatchModel createMatchmakerMatch(final MatchmakerModel matchmaker) {
        final var matchmakerId = matchmaker.getId();

        final var config = new MatchmakerMatchConfigDto(TenantVersionModeDto.create("mode", 2, 16));
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmakerId, config);
        final var syncMatchmakerMatchRequest = new SyncMatchmakerMatchRequest(matchmakerMatch);
        matchmakerService.execute(syncMatchmakerMatchRequest);
        return matchmakerMatch;
    }

    public MatchmakerMatchRuntimeRefModel createMatchmakerMatchRuntimeRef(final MatchmakerMatchModel matchmakerMatch,
                                                                          final RuntimeModel runtime) {
        final var matchmakerId = matchmakerMatch.getMatchmakerId();
        final var matchId = matchmakerMatch.getId();
        final var runtimeId = runtime.getId();
        final var matchmakerMatchRuntimeRef = matchmakerMatchRuntimeRefModelFactory.create(matchmakerId,
                matchId,
                runtimeId);
        final var syncMatchmakerMatchRuntimeRefRequest = new SyncMatchmakerMatchRuntimeRefRequest(
                matchmakerMatchRuntimeRef);
        matchmakerService.execute(syncMatchmakerMatchRuntimeRefRequest);
        return matchmakerMatchRuntimeRef;
    }

    public MatchmakerMatchAssignmentModel createMatchmakerMatchAssignment(final MatchmakerMatchModel matchmakerMatch,
                                                                          final ClientModel client) {
        final var matchmakerId = matchmakerMatch.getMatchmakerId();
        final var matchId = matchmakerMatch.getId();

        final var userId = client.getUserId();
        final var clientId = client.getId();

        final var config = new MatchmakerMatchAssignmentConfigDto();
        final var matchmakerMatchAssignment = matchmakerMatchAssignmentModelFactory.create(matchmakerId,
                matchId,
                userId,
                clientId,
                "players",
                config);
        final var request = new SyncMatchmakerMatchAssignmentRequest(matchmakerMatchAssignment);
        matchmakerService.execute(request);
        return matchmakerMatchAssignment;
    }
}

package com.omgservers.testDataFactory;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerAssignmentRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchClientRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerMatchRuntimeRefRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchConfigModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientConfigModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.factory.matchmaker.MatchmakerAssignmentModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchClientModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchRuntimeRefModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerModelFactory;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.testInterface.MatchmakerServiceTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerTestDataFactory {

    final MatchmakerServiceTestInterface matchmakerService;

    final MatchmakerMatchRuntimeRefModelFactory matchmakerMatchRuntimeRefModelFactory;
    final MatchmakerMatchClientModelFactory matchmakerMatchClientModelFactory;
    final MatchmakerAssignmentModelFactory matchmakerAssignmentModelFactory;
    final MatchmakerMatchModelFactory matchmakerMatchModelFactory;
    final MatchmakerModelFactory matchmakerModelFactory;

    public MatchmakerModel createMatchmaker(final TenantModel tenant,
                                            final VersionModel version) {
        final var tenantId = tenant.getId();
        final var versionId = version.getId();

        final var matchmaker = matchmakerModelFactory.create(tenantId, versionId);
        final var syncMatchmakerRequest = new SyncMatchmakerRequest(matchmaker);
        matchmakerService.syncMatchmaker(syncMatchmakerRequest);
        return matchmaker;
    }

    public MatchmakerAssignmentModel createMatchmakerAssignment(final MatchmakerModel matchmaker,
                                                                final ClientModel client) {
        final var matchmakerId = matchmaker.getId();
        final var clientId = client.getId();

        final var matchmakerAssignment = matchmakerAssignmentModelFactory.create(matchmakerId, clientId);
        final var syncMatchmakerAssignmentRequest = new SyncMatchmakerAssignmentRequest(matchmakerAssignment);
        matchmakerService.syncMatchmakerAssignment(syncMatchmakerAssignmentRequest);
        return matchmakerAssignment;
    }

    public MatchmakerMatchModel createMatchmakerMatch(MatchmakerModel matchmaker) {
        final var matchmakerId = matchmaker.getId();

        final var config = new MatchmakerMatchConfigModel(VersionModeModel.create("mode", 2, 16));
        final var matchmakerMatch = matchmakerMatchModelFactory.create(matchmakerId, config);
        final var syncMatchmakerMatchRequest = new SyncMatchmakerMatchRequest(matchmakerMatch);
        matchmakerService.syncMatchmakerMatch(syncMatchmakerMatchRequest);
        return matchmakerMatch;
    }

    public MatchmakerMatchRuntimeRefModel createMatchmakerMatchRuntimeRef(MatchmakerMatchModel matchmakerMatch,
                                                                          RuntimeModel runtime) {
        final var matchmakerId = matchmakerMatch.getMatchmakerId();
        final var matchId = matchmakerMatch.getId();
        final var runtimeId = runtime.getId();
        final var matchmakerMatchRuntimeRef = matchmakerMatchRuntimeRefModelFactory.create(matchmakerId,
                matchId,
                runtimeId);
        final var syncMatchmakerMatchRuntimeRefRequest = new SyncMatchmakerMatchRuntimeRefRequest(
                matchmakerMatchRuntimeRef);
        matchmakerService.syncMatchmakerMatchRuntimeRef(syncMatchmakerMatchRuntimeRefRequest);
        return matchmakerMatchRuntimeRef;
    }

    public MatchmakerMatchClientModel createMatchmakerMatchClient(MatchmakerMatchModel matchmakerMatch,
                                                                  ClientModel client) {
        final var matchmakerId = matchmakerMatch.getMatchmakerId();
        final var matchId = matchmakerMatch.getId();

        final var userId = client.getUserId();
        final var clientId = client.getId();

        final var config = new MatchmakerMatchClientConfigModel();
        final var matchmakerMatchClient = matchmakerMatchClientModelFactory.create(matchmakerId,
                matchId,
                userId,
                clientId,
                "players",
                config);
        final var syncMatchmakerMatchClientRequest = new SyncMatchmakerMatchClientRequest(matchmakerMatchClient);
        matchmakerService.syncMatchmakerMatchClient(syncMatchmakerMatchClientRequest);
        return matchmakerMatchClient;
    }
}

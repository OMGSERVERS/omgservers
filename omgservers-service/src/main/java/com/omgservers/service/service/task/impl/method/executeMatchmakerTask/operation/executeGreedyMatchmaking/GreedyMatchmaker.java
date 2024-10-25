package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.executeGreedyMatchmaking;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;

@Slf4j
public class GreedyMatchmaker {

    final TenantVersionModeDto config;

    final Map<Long, GreedyMatch> greedyMatchIndexById;
    final TreeSet<GreedyMatch> greedyMatchesSortedSet;

    public GreedyMatchmaker(final TenantVersionModeDto config) {
        this.config = config;

        greedyMatchesSortedSet = new TreeSet<>(Comparator.comparingInt(GreedyMatch::getSize));
        greedyMatchIndexById = new HashMap<>();
    }

    public void addMatchmakerMatch(final MatchmakerMatchModel matchmakerMatch) {
        final var matchmakerMatchId = matchmakerMatch.getId();
        final var greedyMatch = new GreedyMatch(config, matchmakerMatch);
        greedyMatchIndexById.put(matchmakerMatchId, greedyMatch);
        greedyMatchesSortedSet.add(greedyMatch);
    }

    public void addMatchmakerMatchAssignment(final MatchmakerMatchAssignmentModel matchmakerMatchAssignment) {
        final var matchmakerMatchId = matchmakerMatchAssignment.getMatchId();
        final var greedyMatch = greedyMatchIndexById.get(matchmakerMatchId);
        if (Objects.nonNull(greedyMatch)) {
            greedyMatch.addAssignment(matchmakerMatchAssignment);
        } else {
            log.error("Failed to add assignment, match was not found, {}", matchmakerMatchAssignment);
        }
    }

    public Optional<GreedyResult> matchRequest(final MatchmakerRequestModel matchmakerRequest) {
        for (final var greedyMatch : greedyMatchesSortedSet) {
            final var greedyGroupOptional = greedyMatch.matchRequest(matchmakerRequest);
            if (greedyGroupOptional.isPresent()) {
                final var greedyGroup = greedyGroupOptional.get();
                return Optional.of(new GreedyResult(greedyMatch, greedyGroup));
            }
        }

        return Optional.empty();
    }

    public List<GreedyMatch> getReadyMatches() {
        return greedyMatchesSortedSet.stream()
                .filter(GreedyMatch::checkReadiness)
                .toList();
    }
}

package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.executeGreedyMatchmaking;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionGroupDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GreedyGroup {

    final TenantVersionGroupDto config;
    final List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignment;

    public GreedyGroup(final TenantVersionGroupDto config) {
        this.config = config;
        matchmakerMatchAssignment = new ArrayList<>();
    }

    public void addAssignment(final MatchmakerMatchAssignmentModel assignment) {
        matchmakerMatchAssignment.add(assignment);
    }

    boolean matchRequest(final MatchmakerRequestModel request) {
        final var maxPlayers = config.getMaxPlayers();
        final var groupSize = getSize();
        if (groupSize >= maxPlayers) {
            return false;
        }

        // TODO: The request will likely include additional fields for matchmaking.

        return true;
    }

    boolean checkReadiness() {
        final var minPlayers = config.getMinPlayers();
        final var groupSize = getSize();
        return groupSize >= minPlayers;
    }

    int getSize() {
        return matchmakerMatchAssignment.size();
    }

    String getName() {
        return config.getName();
    }
}

package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.component;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionGroupDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MatcherGroup {

    @Getter
    final TenantVersionGroupDto config;

    final List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignment;

    boolean completed;

    public MatcherGroup(final TenantVersionGroupDto config) {
        this.config = config;
        matchmakerMatchAssignment = new ArrayList<>();
        completed = false;
    }

    void addAssignment(final MatchmakerMatchAssignmentModel assignment) {
        matchmakerMatchAssignment.add(assignment);
    }

    boolean matchRequest(final MatchmakerRequestModel request) {
        final var maxPlayers = config.getMaxPlayers();
        final var groupSize = getSize();
        if (groupSize >= maxPlayers) {
            return false;
        }

        // TODO: The request will likely include additional fields for matchmaking later

        return true;
    }

    boolean checkReadiness() {
        if (!completed) {
            final var minPlayers = config.getMinPlayers();
            final var groupSize = getSize();
            completed = groupSize >= minPlayers;
        }

        return completed;
    }

    int getSize() {
        return matchmakerMatchAssignment.size();
    }

    String getName() {
        return config.getName();
    }
}

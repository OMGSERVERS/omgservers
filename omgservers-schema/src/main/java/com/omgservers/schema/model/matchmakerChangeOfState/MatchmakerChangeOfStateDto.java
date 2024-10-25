package com.omgservers.schema.model.matchmakerChangeOfState;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class MatchmakerChangeOfStateDto {

    @NotNull
    Set<MatchmakerAssignmentModel> assignmentsToSync;

    @NotNull
    Set<MatchmakerAssignmentModel> assignmentsToDelete;

    @NotNull
    Set<MatchmakerCommandModel> commandsToDelete;

    @NotNull
    Set<MatchmakerRequestModel> requestsToDelete;

    @NotNull
    Set<MatchmakerMatchModel> matchesToSync;

    @NotNull
    Set<MatchmakerMatchModel> matchesToUpdateStatus;

    @NotNull
    Set<MatchmakerMatchModel> matchesToDelete;

    @NotNull
    Set<MatchmakerMatchAssignmentModel> matchAssignmentsToSync;

    @NotNull
    Set<MatchmakerMatchAssignmentModel> matchAssignmentsToDelete;

    public MatchmakerChangeOfStateDto() {
        assignmentsToSync = new HashSet<>();
        assignmentsToDelete = new HashSet<>();
        commandsToDelete = new HashSet<>();
        requestsToDelete = new HashSet<>();
        matchesToSync = new HashSet<>();
        matchesToUpdateStatus = new HashSet<>();
        matchesToDelete = new HashSet<>();
        matchAssignmentsToSync = new HashSet<>();
        matchAssignmentsToDelete = new HashSet<>();
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return !commandsToDelete.isEmpty() ||
                !requestsToDelete.isEmpty() ||
                !assignmentsToSync.isEmpty() ||
                !assignmentsToDelete.isEmpty() ||
                !matchesToSync.isEmpty() ||
                !matchesToUpdateStatus.isEmpty() ||
                !matchesToDelete.isEmpty() ||
                !matchAssignmentsToSync.isEmpty() ||
                !matchAssignmentsToDelete.isEmpty();
    }

    @ToString.Include
    public int commandsToDelete() {
        return commandsToDelete.size();
    }

    @ToString.Include
    public int requestsToDelete() {
        return requestsToDelete.size();
    }

    @ToString.Include
    public int assignmentsToSync() {
        return assignmentsToSync.size();
    }

    @ToString.Include
    public int assignmentsToDelete() {
        return assignmentsToDelete.size();
    }

    @ToString.Include
    public int matchesToSync() {
        return matchesToSync.size();
    }

    @ToString.Include
    public int matchesToUpdateStatus() {
        return matchesToUpdateStatus.size();
    }

    @ToString.Include
    public int matchesToDelete() {
        return matchesToDelete.size();
    }

    @ToString.Include
    public int matchAssignmentsToSync() {
        return matchAssignmentsToSync.size();
    }

    @ToString.Include
    public int matchAssignmentsToDelete() {
        return matchAssignmentsToDelete.size();
    }
}

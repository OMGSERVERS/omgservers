package com.omgservers.schema.model.matchmakerChangeOfState;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MatchmakerChangeOfStateDto {

    @NotNull
    List<Long> matchmakerCommandsToDelete;

    @NotNull
    List<Long> matchmakerRequestsToDelete;

    @Valid
    @NotNull
    List<MatchmakerMatchResourceModel> matchmakerMatchResourcesToSync;

    @Valid
    @NotNull
    List<MatchmakerMatchResourceToUpdateStatusDto> matchmakerMatchResourcesToUpdateStatus;

    @NotNull
    List<Long> matchmakerMatchResourcesToDelete;

    @Valid
    @NotNull
    List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignmentsToSync;

    @NotNull
    List<Long> matchmakerMatchAssignmentsToDelete;

    public MatchmakerChangeOfStateDto() {
        matchmakerCommandsToDelete = new ArrayList<>();
        matchmakerRequestsToDelete = new ArrayList<>();
        matchmakerMatchResourcesToSync = new ArrayList<>();
        matchmakerMatchResourcesToUpdateStatus = new ArrayList<>();
        matchmakerMatchResourcesToDelete = new ArrayList<>();
        matchmakerMatchAssignmentsToSync = new ArrayList<>();
        matchmakerMatchAssignmentsToDelete = new ArrayList<>();
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return !matchmakerCommandsToDelete.isEmpty() ||
                !matchmakerRequestsToDelete.isEmpty() ||
                !matchmakerMatchResourcesToSync.isEmpty() ||
                !matchmakerMatchResourcesToUpdateStatus.isEmpty() ||
                !matchmakerMatchResourcesToDelete.isEmpty() ||
                !matchmakerMatchAssignmentsToSync.isEmpty() ||
                !matchmakerMatchAssignmentsToDelete.isEmpty();
    }

    @ToString.Include
    public int matchmakerCommandsToDelete() {
        return matchmakerCommandsToDelete.size();
    }

    @ToString.Include
    public int matchmakerRequestsToDelete() {
        return matchmakerRequestsToDelete.size();
    }


    @ToString.Include
    public int matchmakerMatchResourcesToSync() {
        return matchmakerMatchResourcesToSync.size();
    }

    @ToString.Include
    public int matchmakerMatchResourcesToUpdateStatus() {
        return matchmakerMatchResourcesToUpdateStatus.size();
    }

    @ToString.Include
    public int matchmakerMatchResourcesToDelete() {
        return matchmakerMatchResourcesToDelete.size();
    }

    @ToString.Include
    public int matchmakerMatchAssignmentsToSync() {
        return matchmakerMatchAssignmentsToSync.size();
    }

    @ToString.Include
    public int matchmakerMatchAssignmentsToDelete() {
        return matchmakerMatchAssignmentsToDelete.size();
    }
}

package com.omgservers.schema.model.deploymentChangeOfState;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DeploymentChangeOfStateDto {

    @NotNull
    List<Long> deploymentCommandsToDelete;

    @NotNull
    List<Long> deploymentRequestsToDelete;

    /*
    LobbyResource
     */

    @Valid
    @NotNull
    List<DeploymentLobbyResourceModel> deploymentLobbyResourcesToSync;

    @Valid
    @NotNull
    List<DeploymentLobbyResourceToUpdateStatusDto> deploymentLobbyResourcesToUpdateStatus;

    @NotNull
    List<Long> deploymentLobbyResourcesToDelete;

    /*
    LobbyAssignment
     */

    @Valid
    @NotNull
    List<DeploymentLobbyAssignmentModel> deploymentLobbyAssignmentToSync;

    @NotNull
    List<Long> deploymentLobbyAssignmentToDelete;

    /*
    MatchmakerResource
     */

    @Valid
    @NotNull
    List<DeploymentMatchmakerResourceModel> deploymentMatchmakerResourcesToSync;

    @Valid
    @NotNull
    List<DeploymentMatchmakerResourceToUpdateStatusDto> deploymentMatchmakerResourcesToUpdateStatus;

    @Valid
    @NotNull
    List<Long> deploymentMatchmakerResourcesToDelete;

    /*
    MatchmakerAssignment
     */

    @Valid
    @NotNull
    List<DeploymentMatchmakerAssignmentModel> deploymentMatchmakerAssignmentToSync;

    @NotNull
    List<Long> deploymentMatchmakerAssignmentToDelete;

    public DeploymentChangeOfStateDto() {
        deploymentCommandsToDelete = new ArrayList<>();
        deploymentRequestsToDelete = new ArrayList<>();
        deploymentLobbyResourcesToSync = new ArrayList<>();
        deploymentLobbyResourcesToUpdateStatus = new ArrayList<>();
        deploymentLobbyResourcesToDelete = new ArrayList<>();
        deploymentLobbyAssignmentToSync = new ArrayList<>();
        deploymentLobbyAssignmentToDelete = new ArrayList<>();
        deploymentMatchmakerResourcesToSync = new ArrayList<>();
        deploymentMatchmakerResourcesToUpdateStatus = new ArrayList<>();
        deploymentMatchmakerResourcesToDelete = new ArrayList<>();
        deploymentMatchmakerAssignmentToSync = new ArrayList<>();
        deploymentMatchmakerAssignmentToDelete = new ArrayList<>();
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return !deploymentCommandsToDelete.isEmpty() ||
                !deploymentRequestsToDelete.isEmpty() ||
                !deploymentLobbyResourcesToSync.isEmpty() ||
                !deploymentLobbyResourcesToUpdateStatus.isEmpty() ||
                !deploymentLobbyResourcesToDelete.isEmpty() ||
                !deploymentLobbyAssignmentToSync.isEmpty() ||
                !deploymentLobbyAssignmentToDelete.isEmpty() ||
                !deploymentMatchmakerResourcesToSync.isEmpty() ||
                !deploymentMatchmakerResourcesToUpdateStatus.isEmpty() ||
                !deploymentMatchmakerResourcesToDelete.isEmpty() ||
                !deploymentMatchmakerAssignmentToSync.isEmpty() ||
                !deploymentMatchmakerAssignmentToDelete.isEmpty();
    }

    @ToString.Include
    public int deploymentCommandsToDelete() {
        return deploymentCommandsToDelete.size();
    }

    @ToString.Include
    public int deploymentRequestsToDelete() {
        return deploymentRequestsToDelete.size();
    }

    @ToString.Include
    public int deploymentLobbyResourcesToSync() {
        return deploymentLobbyResourcesToSync.size();
    }

    @ToString.Include
    public int deploymentLobbyResourcesToUpdateStatus() {
        return deploymentLobbyResourcesToUpdateStatus.size();
    }

    @ToString.Include
    public int deploymentLobbyResourcesToDelete() {
        return deploymentLobbyResourcesToDelete.size();
    }

    @ToString.Include
    public int deploymentLobbyAssignmentToSync() {
        return deploymentLobbyAssignmentToSync.size();
    }

    @ToString.Include
    public int deploymentLobbyAssignmentToDelete() {
        return deploymentLobbyAssignmentToDelete.size();
    }

    @ToString.Include
    public int deploymentMatchmakerResourcesToSync() {
        return deploymentMatchmakerResourcesToSync.size();
    }

    @ToString.Include
    public int deploymentMatchmakerResourcesToUpdateStatus() {
        return deploymentMatchmakerResourcesToUpdateStatus.size();
    }

    @ToString.Include
    public int deploymentMatchmakerResourcesToDelete() {
        return deploymentMatchmakerResourcesToDelete.size();
    }

    @ToString.Include
    public int deploymentMatchmakerAssignmentToSync() {
        return deploymentMatchmakerAssignmentToSync.size();
    }

    @ToString.Include
    public int deploymentMatchmakerAssignmentToDelete() {
        return deploymentMatchmakerAssignmentToDelete.size();
    }
}

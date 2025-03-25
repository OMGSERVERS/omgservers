package com.omgservers.schema.model.runtimeChangeOfState;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class RuntimeChangeOfStateDto {

    List<Long> runtimeCommandsToDelete;

    @NotNull
    List<RuntimeAssignmentModel> runtimeAssignmentToSync;

    @NotNull
    List<Long> runtimeAssignmentToDelete;

    public RuntimeChangeOfStateDto() {
        runtimeCommandsToDelete = new ArrayList<>();
        runtimeAssignmentToSync = new ArrayList<>();
        runtimeAssignmentToDelete = new ArrayList<>();
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return !runtimeCommandsToDelete.isEmpty() ||
                !runtimeAssignmentToSync.isEmpty() ||
                !runtimeAssignmentToDelete.isEmpty();
    }

    @ToString.Include
    public int runtimeCommandsToDelete() {
        return runtimeCommandsToDelete.size();
    }

    @ToString.Include
    public int runtimeAssignmentToSync() {
        return runtimeAssignmentToSync.size();
    }

    @ToString.Include
    public int runtimeAssignmentToDelete() {
        return runtimeAssignmentToDelete.size();
    }
}

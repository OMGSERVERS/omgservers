package com.omgservers.schema.model.poolChangeOfState;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PoolChangeOfStateDto {

    @NotNull
    List<Long> poolCommandsToDelete;

    @NotNull
    List<Long> poolRequestsToDelete;

    @Valid
    @NotNull
    List<PoolServerModel> poolServersToSync;

    @NotNull
    List<Long> poolServersToDelete;

    @Valid
    @NotNull
    List<PoolContainerModel> poolContainersToSync;

    @NotNull
    List<Long> poolContainersToDelete;

    public PoolChangeOfStateDto() {
        poolCommandsToDelete = new ArrayList<>();
        poolRequestsToDelete = new ArrayList<>();
        poolServersToSync = new ArrayList<>();
        poolServersToDelete = new ArrayList<>();
        poolContainersToSync = new ArrayList<>();
        poolContainersToDelete = new ArrayList<>();
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return !poolCommandsToDelete.isEmpty() ||
                !poolRequestsToDelete.isEmpty() ||
                !poolServersToSync.isEmpty() ||
                !poolServersToDelete.isEmpty() ||
                !poolContainersToSync.isEmpty() ||
                !poolContainersToDelete.isEmpty();
    }

    @ToString.Include
    public int poolCommandsToDelete() {
        return poolCommandsToDelete.size();
    }

    @ToString.Include
    public int poolRequestsToDelete() {
        return poolRequestsToDelete.size();
    }

    @ToString.Include
    public int poolServersToSync() {
        return poolServersToSync.size();
    }

    @ToString.Include
    public int poolServersToDelete() {
        return poolServersToDelete.size();
    }

    @ToString.Include
    public int poolContainersToSync() {
        return poolContainersToSync.size();
    }

    @ToString.Include
    public int poolContainersToDelete() {
        return poolContainersToDelete.size();
    }
}

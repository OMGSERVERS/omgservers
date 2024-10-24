package com.omgservers.schema.model.poolChangeOfState;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class PoolChangeOfStateDto {

    @NotNull
    Set<PoolServerModel> serversToSync;

    @NotNull
    Set<PoolServerModel> serversToDelete;

    @NotNull
    Set<PoolContainerModel> containersToSync;

    @NotNull
    Set<PoolContainerModel> containersToDelete;

    @NotNull
    Set<PoolRequestModel> requestsToDelete;

    public PoolChangeOfStateDto() {
        serversToSync = new HashSet<>();
        serversToDelete = new HashSet<>();
        containersToSync = new HashSet<>();
        containersToDelete = new HashSet<>();
        requestsToDelete = new HashSet<>();
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return serversToSync.size() > 0 ||
                serversToDelete.size() > 0 ||
                containersToSync.size() > 0 ||
                containersToDelete.size() > 0 ||
                requestsToDelete.size() > 0;
    }

    @ToString.Include
    public int serversToSync() {
        return serversToSync.size();
    }

    @ToString.Include
    public int serversToDelete() {
        return serversToDelete.size();
    }

    @ToString.Include
    public int containersToSync() {
        return containersToSync.size();
    }

    @ToString.Include
    public int containersToDelete() {
        return containersToDelete.size();
    }

    @ToString.Include
    public int requestsToDelete() {
        return requestsToDelete.size();
    }
}

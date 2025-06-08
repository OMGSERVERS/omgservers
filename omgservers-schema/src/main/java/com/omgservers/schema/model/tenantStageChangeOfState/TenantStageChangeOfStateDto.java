package com.omgservers.schema.model.tenantStageChangeOfState;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TenantStageChangeOfStateDto {

    @NotNull
    List<Long> tenantStageCommandsToDelete;

    @NotNull
    List<TenantDeploymentResourceModel> tenantDeploymentResourcesToDelete;

    @NotNull
    List<TenantStageDeploymentResourceToUpdateStatusDto> tenantDeploymentResourcesToUpdateStatus;

    public TenantStageChangeOfStateDto() {
        tenantStageCommandsToDelete = new ArrayList<>();
        tenantDeploymentResourcesToDelete = new ArrayList<>();
        tenantDeploymentResourcesToUpdateStatus = new ArrayList<>();
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return !tenantStageCommandsToDelete.isEmpty() 
                || !tenantDeploymentResourcesToDelete.isEmpty()
                || !tenantDeploymentResourcesToUpdateStatus.isEmpty();
    }

    @ToString.Include
    public int tenantStageCommandsToDelete() {
        return tenantStageCommandsToDelete.size();
    }

    @ToString.Include
    public int tenantDeploymentResourcesToDelete() {
        return tenantDeploymentResourcesToDelete.size();
    }

    @ToString.Include
    public int tenantDeploymentResourcesToUpdateStatus() {
        return tenantDeploymentResourcesToUpdateStatus.size();
    }
}

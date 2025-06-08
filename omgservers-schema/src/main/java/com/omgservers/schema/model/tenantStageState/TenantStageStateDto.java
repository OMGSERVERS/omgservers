package com.omgservers.schema.model.tenantStageState;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantStageStateDto {

    @NotNull
    TenantStageModel tenantStage;

    @NotNull
    List<TenantStageCommandModel> tenantStageCommands;

    @NotNull
    List<TenantDeploymentResourceModel> tenantDeploymentResources;
}

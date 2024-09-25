package com.omgservers.schema.module.tenant.tenantDeployment.dto;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDeploymentDataDto {

    @NotNull
    TenantDeploymentModel tenantDeployment;

    @NotNull
    List<TenantLobbyRefModel> tenantLobbyRefs;

    @NotNull
    List<TenantMatchmakerRefModel> tenantMatchmakerRefs;
}

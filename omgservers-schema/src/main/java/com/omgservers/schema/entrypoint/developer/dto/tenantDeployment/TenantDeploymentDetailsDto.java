package com.omgservers.schema.entrypoint.developer.dto.tenantDeployment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDeploymentDetailsDto {

    TenantDeploymentDto deployment;

    List<TenantLobbyRefDto> lobbyRefs;

    List<TenantMatchmakerRefDto> matchmakerRefs;
}

package com.omgservers.schema.entrypoint.developer.dto.tenantDeployment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentMatchmakerResourceDto {

    Long id;

    Instant created;

    Long matchmakerId;

}

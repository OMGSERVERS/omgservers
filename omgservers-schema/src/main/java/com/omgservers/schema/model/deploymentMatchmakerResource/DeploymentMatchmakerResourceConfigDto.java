package com.omgservers.schema.model.deploymentMatchmakerResource;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.omgservers.schema.model.matchmaker.MatchmakerConfigDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentMatchmakerResourceConfigDto {

    @NotNull
    @JsonSetter(nulls = Nulls.SKIP)
    DeploymentMatchmakerResourceConfigVersionEnum version = DeploymentMatchmakerResourceConfigVersionEnum.V1;

    @NotNull
    @JsonSetter(nulls = Nulls.SKIP)
    MatchmakerConfigDto matchmakerConfig = new MatchmakerConfigDto();
}

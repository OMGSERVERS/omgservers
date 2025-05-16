package com.omgservers.schema.model.deployment;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentConfigDto {

    @NotNull
    @JsonSetter(nulls = Nulls.SKIP)
    DeploymentConfigVersionEnum version = DeploymentConfigVersionEnum.V1;

    @PositiveOrZero
    @JsonSetter(nulls = Nulls.SKIP)
    Integer minLobbies = 0;

    @Positive
    @JsonSetter(nulls = Nulls.SKIP)
    Integer maxLobbies = Integer.MAX_VALUE;

    @Positive
    @JsonSetter(nulls = Nulls.SKIP)
    Integer maxMatchmakers = Integer.MAX_VALUE;

    @Valid
    @JsonSetter(nulls = Nulls.SKIP)
    DeploymentConfigLobbyDto lobby = new DeploymentConfigLobbyDto();

    @Valid
    @JsonSetter(nulls = Nulls.SKIP)
    DeploymentConfigMatchmakerDto matchmaker = new DeploymentConfigMatchmakerDto();
}

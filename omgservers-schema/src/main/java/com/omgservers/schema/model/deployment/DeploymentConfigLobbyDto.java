package com.omgservers.schema.model.deployment;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentConfigLobbyDto {

    @Positive
    @JsonSetter(nulls = Nulls.SKIP)
    Integer maxAssignments = 128;

    @PositiveOrZero
    @JsonSetter(nulls = Nulls.SKIP)
    Integer minLifetime = 0;
}

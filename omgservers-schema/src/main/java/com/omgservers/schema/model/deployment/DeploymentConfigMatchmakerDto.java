package com.omgservers.schema.model.deployment;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentConfigMatchmakerDto {

    @Positive
    @JsonSetter(nulls = Nulls.SKIP)
    Integer maxAssignments = 1024;

    @Positive
    @JsonSetter(nulls = Nulls.SKIP)
    Integer maxMatches = Integer.MAX_VALUE;

    @Valid
    @JsonSetter(nulls = Nulls.SKIP)
    Map<String, DeploymentConfigMatchmakerModeDto> modes = new HashMap<>();
}

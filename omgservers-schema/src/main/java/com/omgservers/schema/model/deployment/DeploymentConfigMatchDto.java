package com.omgservers.schema.model.deployment;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentConfigMatchDto {

    @PositiveOrZero
    @JsonSetter(nulls = Nulls.SKIP)
    Long cpuLimit = 0L;

    @PositiveOrZero
    @JsonSetter(nulls = Nulls.SKIP)
    Long memoryLimit = 0L;
}

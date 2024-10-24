package com.omgservers.schema.model.poolSeverContainer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolContainerConfigDto {

    static public PoolContainerConfigDto create() {
        final var poolContainerConfigDto = new PoolContainerConfigDto();
        return poolContainerConfigDto;
    }

    @NotNull
    String imageId;

    @NotNull
    Long cpuLimitInMilliseconds;

    @NotNull
    Long memoryLimitInMegabytes;

    @NotNull
    Map<String, String> environment;
}

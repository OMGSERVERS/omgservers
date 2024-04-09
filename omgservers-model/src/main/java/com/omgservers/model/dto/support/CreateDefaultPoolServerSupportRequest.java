package com.omgservers.model.dto.support;

import com.omgservers.model.poolServer.PoolServerQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDefaultPoolServerSupportRequest {

    @NotNull
    PoolServerQualifierEnum qualifier;

    @NotNull
    URI dockerDaemonUri;

    @NotNull
    Integer cpuCount;

    @NotNull
    Integer memorySize;

    @NotNull
    Integer maxContainers;
}

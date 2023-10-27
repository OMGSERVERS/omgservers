package com.omgservers.dto.gateway;

import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRuntimeRequest {

    @NotNull
    URI server;

    @NotNull
    Long connectionId;

    @NotNull
    AssignedRuntimeModel assignedRuntime;
}

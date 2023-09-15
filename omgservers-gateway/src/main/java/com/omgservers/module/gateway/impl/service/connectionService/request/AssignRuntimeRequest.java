package com.omgservers.module.gateway.impl.service.connectionService.request;

import com.omgservers.model.assignedRuntime.AssignedRuntimeModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRuntimeRequest {

    @NotNull
    Long connectionId;

    @NotNull
    AssignedRuntimeModel assignedRuntime;
}

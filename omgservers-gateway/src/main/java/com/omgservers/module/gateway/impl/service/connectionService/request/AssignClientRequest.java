package com.omgservers.module.gateway.impl.service.connectionService.request;

import com.omgservers.model.assignedClient.AssignedClientModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignClientRequest {

    @NotNull
    Long connectionId;

    @NotNull
    AssignedClientModel assignedClient;
}

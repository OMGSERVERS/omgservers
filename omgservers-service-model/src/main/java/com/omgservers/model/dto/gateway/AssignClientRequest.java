package com.omgservers.model.dto.gateway;

import com.omgservers.model.assignedClient.AssignedClientModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignClientRequest {

    @NotNull
    URI server;

    @NotNull
    Long connectionId;

    @NotNull
    AssignedClientModel assignedClient;
}

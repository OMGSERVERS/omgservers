package com.omgservers.service.server.state.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetNodeIdRequest {

    @NotNull
    Long nodeId;
}

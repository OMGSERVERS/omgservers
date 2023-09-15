package com.omgservers.module.gateway.impl.service.connectionService.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSessionRequest {

    @NotNull
    Long connectionId;
}

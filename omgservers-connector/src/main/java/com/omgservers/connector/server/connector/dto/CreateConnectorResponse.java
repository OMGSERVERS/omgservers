package com.omgservers.connector.server.connector.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateConnectorResponse {

    @NotNull
    Boolean created;
}

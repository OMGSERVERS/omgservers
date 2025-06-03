package com.omgservers.schema.entrypoint.connector;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenConnectorResponse {

    @ToString.Exclude
    String rawToken;
}

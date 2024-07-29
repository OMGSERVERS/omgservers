package com.omgservers.schema.entrypoint.router;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenRouterResponse {

    @ToString.Exclude
    String rawToken;
}

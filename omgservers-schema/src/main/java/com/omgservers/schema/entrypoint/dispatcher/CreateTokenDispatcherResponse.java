package com.omgservers.schema.entrypoint.dispatcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenDispatcherResponse {

    @ToString.Exclude
    String rawToken;
}

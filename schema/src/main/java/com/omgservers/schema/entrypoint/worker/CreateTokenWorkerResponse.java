package com.omgservers.schema.entrypoint.worker;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenWorkerResponse {

    @ToString.Exclude
    String rawToken;
}

package com.omgservers.schema.entrypoint.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BcryptHashServerResponse {

    String hash;
}

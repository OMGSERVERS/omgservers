package com.omgservers.schema.entrypoint.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientPlayerResponse {

    Long clientId;

    URI connectionUrl;
}

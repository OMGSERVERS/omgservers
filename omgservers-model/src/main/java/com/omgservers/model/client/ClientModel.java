package com.omgservers.model.client;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel {

    static public void validate(ClientModel client) {
        if (client == null) {
            throw new ServerSideBadRequestException("client is null");
        }
    }

    Long id;
    Long playerId;
    Instant created;
    URI server;
    Long connectionId;
}
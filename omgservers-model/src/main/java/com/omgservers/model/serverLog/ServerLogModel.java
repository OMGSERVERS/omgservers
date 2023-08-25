package com.omgservers.model.serverLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerLogModel {

    static public void validate(ServerLogModel log) {
        if (log == null) {
            throw new IllegalArgumentException("log is null");
        }
    }

    Long id;
    Instant created;
    URI server;
    String message;
}

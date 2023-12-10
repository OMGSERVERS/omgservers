package com.omgservers.model.serverLog;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerLogModel {

    @NotNull
    Long id;

    @NotNull
    Instant created;

    @NotNull
    URI server;

    @NotNull
    String message;
}
